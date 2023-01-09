package com.weather.windsurfingweather.service;

import com.weather.windsurfingweather.exceptions.DateOutOfRangeException;
import com.weather.windsurfingweather.exceptions.LocationAlreadyExistsException;
import com.weather.windsurfingweather.exceptions.NoForecastFoundException;
import com.weather.windsurfingweather.model.GeographicalCoordinates;
import com.weather.windsurfingweather.model.Location;
import com.weather.windsurfingweather.model.record.LocationRecord;
import com.weather.windsurfingweather.repo.LocationRepository;
import com.weather.windsurfingweather.utils.ApiKey;
import com.weather.windsurfingweather.utils.Calculation;
import com.weather.windsurfingweather.utils.ContentDownload;
import com.weather.windsurfingweather.utils.DateRange;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.weather.windsurfingweather.utils.ContentDownload.getDataFromUrl;

@Service
@Slf4j
@AllArgsConstructor
//tak samo Service interfejs i WeatherServiceImpl
public class WeatherService {

    LocationRepository locationRepository;


    public LocationRecord getBestLocation(LocalDate date) throws IOException {
        //90% przypadkow gdy uzywasz zmiennej tylko raz to daj bezposrednio w miejsce uzycia
        Map<String, LocationRecord> matchingLocations = getMatchingLocations(date);

        Map<String, LocationRecord> filteredLocations = Calculation.filterByRequirements(matchingLocations);

        Map<String, Long> mapOfValuesFromFormula = Calculation.calculateValueFromFormula(filteredLocations);

        LocationRecord location = null;
// zewnetrznego ifa lepiej byloby wywalic do osbnej metody i zrobic na Optionalu return mapOfBestLocation.orElse(null)
        if (!mapOfValuesFromFormula.isEmpty()) {

            Map.Entry<String, Long> maxEntry = mapOfValuesFromFormula.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(NoSuchElementException::new);

            Optional<Map.Entry<String, LocationRecord>> mapOfBestLocation = filteredLocations.entrySet()
                .stream()
                .filter(o -> o.getValue().locationName().equals(maxEntry.getKey()))
                .findAny();

            //nie zagniejdzaj ifow
            if (mapOfBestLocation.isPresent()) {
                //jesli mapOfBestLocation bedzie nullem i ten if sie nie wykona to i tak na koncu zwrocisz location == null;
                location = mapOfBestLocation.get().getValue();
            }
        }

        return location;
    }

    //za duzo logiki w serwisie, dalbym dodatkowe klasy helper/util

    private Map<String, LocationRecord> getMatchingLocations(LocalDate date) throws IOException {
        if (DateRange.isInRange(date)) {
            Map<String, LocationRecord> locations = new HashMap<>();

            List<Location> allLocations = locationRepository.findAll();

            for (Location location : allLocations) {
                JSONArray dataFromUrl = getDataFromUrl(location.getUrl());
                List<JSONObject> weatherForAllDays = new ArrayList<>();
                for (int i = 0; i < dataFromUrl.length(); i++) {
                    weatherForAllDays.add((JSONObject) dataFromUrl.get(i));
                }

                JSONObject oneDayForecast = weatherForAllDays.stream()
                    .filter(s -> s.getString("datetime").equals(date.toString()))
                    .findAny().orElseThrow(NoForecastFoundException::new);

                LocationRecord newLocation = new LocationRecord(
                    location.getLocationName(),
                    oneDayForecast.getLong("temp"),
                    oneDayForecast.getLong("wind_spd"),
                    new GeographicalCoordinates(location.getLon(), location.getLat()));

                locations.put(location.getLocationName(), newLocation);
            }
            return locations;
        } else {
            throw new DateOutOfRangeException();
        }

    }


    public Location addNewLocation(String locationUrl) throws IOException {


        //uzywasz tego key w jednym miejscu wiec po prostu zrob to jako pole private static final na poziomie serwisu
        String urlWithKey = locationUrl + ApiKey.getKey();
        JSONObject objectFromUrl = ContentDownload.getObjectFromUrl(urlWithKey);
//uzywasz wszystkich zmiennych tylko raz wiec to jedynie dodatkowe linijki kodu
        String cityName = objectFromUrl.getString("city_name");
        String lon = objectFromUrl.getString("lon");
        String lat = objectFromUrl.getString("lat");

        if (!locationRepository.existsByLocationName(cityName)) {
            return locationRepository.save(new Location(urlWithKey, cityName, lon, lat));
        } else {
            throw new LocationAlreadyExistsException();
        }
    }

    public Collection<Location> getAll() {
        return locationRepository.findAll();
    }
}
