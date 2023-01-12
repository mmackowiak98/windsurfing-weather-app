package com.weather.windsurfingweather.service;

import com.weather.windsurfingweather.exceptions.DateOutOfRangeException;
import com.weather.windsurfingweather.exceptions.LocationAlreadyExistsException;
import com.weather.windsurfingweather.helper.LocationHelper;
import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.record.LocationRecord;
import com.weather.windsurfingweather.operation.WeatherService;
import com.weather.windsurfingweather.repo.LocationRepository;
import com.weather.windsurfingweather.util.Calculations;
import com.weather.windsurfingweather.client.WeatherClient;
import com.weather.windsurfingweather.util.DateRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    @Value("${properties.api-key}")
    private String apiKey;
    private final LocationRepository locationRepository;
    private final Calculations calculations;
    private final LocationHelper locationHelper;
    private final WeatherClient weatherClient;

    @Override
    public LocationRecord getLocation(LocalDate date) throws IOException {
        Map<String, LocationRecord> filteredLocations = calculations.filterByRequirements(getMatchingLocations(date));
        Map<String, Long> mapOfValuesFromFormula = calculations.calculateValueFromFormula(filteredLocations);

        return getBestLocation(mapOfValuesFromFormula, filteredLocations).orElse(null);
    }

    @Override
    public Location addNewLocation(String locationUrl) throws IOException {

        String urlWithKey = locationUrl + apiKey;

        JSONObject objectFromUrl = weatherClient.getObjectFromUrl(urlWithKey);

        if (!locationRepository.existsByLocationNameIgnoreCase(objectFromUrl.getString("city_name"))) {
            return locationRepository.save(new Location(
                    urlWithKey,
                    objectFromUrl.getString("city_name"),
                    objectFromUrl.getString("lon"),
                    objectFromUrl.getString("lat")));
        } else {
            throw new LocationAlreadyExistsException();
        }
    }

    @Override
    public Collection<Location> getAll() {
        return locationRepository.findAll();
    }

    private Optional<LocationRecord> getBestLocation(Map<String, Long> mapOfValuesFromFormula, Map<String, LocationRecord> filteredLocations) {

        return mapOfValuesFromFormula.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .flatMap(maxEntry -> filteredLocations.entrySet().stream()
                        .filter(o -> o.getValue().locationName().equals(maxEntry.getKey()))
                        .findAny())
                .map(Map.Entry::getValue);
    }

    public Map<String, LocationRecord> getMatchingLocations(LocalDate date) throws IOException {

        if (DateRange.isInRange(date)) {

            Collection<Location> allLocations = getAll();

            return locationHelper.filterMatchingLocations(date, allLocations);

        } else {
            throw new DateOutOfRangeException();
        }

    }
}
