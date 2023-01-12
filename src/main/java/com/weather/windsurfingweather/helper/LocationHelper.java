package com.weather.windsurfingweather.helper;

import com.weather.windsurfingweather.client.WeatherClient;
import com.weather.windsurfingweather.exceptions.NoForecastFoundException;
import com.weather.windsurfingweather.model.record.GeographicalCoordinatesRecord;
import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.record.LocationRecord;;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Component
@AllArgsConstructor
public class LocationHelper {

    WeatherClient weatherClient;

    public Map<String, LocationRecord> filterMatchingLocations(LocalDate date, Collection<Location> allLocations) throws IOException {
        Map<String, LocationRecord> locations = new HashMap<>();

        for (Location location : allLocations) {
            JSONArray dataFromUrl = weatherClient.getDataFromUrl(location.getUrl());
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
                    new GeographicalCoordinatesRecord(location.getLon(), location.getLat()));

            locations.put(location.getLocationName(), newLocation);
        }
        return locations;
    }
}
