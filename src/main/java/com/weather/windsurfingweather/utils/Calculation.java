package com.weather.windsurfingweather.utils;

import com.weather.windsurfingweather.model.dao.LocationDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculation {
    private static final int MIN_WIND_SPEED = 5;
    private static final int MAX_WIND_SPEED = 18;
    private static final int MIN_TEMPERATURE = 5;
    private static final int MAX_TEMPERATURE = 35;

    public static Map<String, LocationDAO> filterByRequirements(Map<String, LocationDAO> locations) {

        return locations.entrySet().stream()
                .filter(o -> o.getValue().getWindSpeed() > MIN_WIND_SPEED)
                .filter(o -> o.getValue().getWindSpeed() < MAX_WIND_SPEED)
                .filter(o -> o.getValue().getTemperature() > MIN_TEMPERATURE)
                .filter(o -> o.getValue().getTemperature() < MAX_TEMPERATURE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, Long> calculateValueFromFormula(Map<String, LocationDAO> locations) {

        Map<String, Long> mapOfValuesFromFormula = new HashMap<>();

        Long surfingScore;

        for (Map.Entry<String, LocationDAO> entry : locations.entrySet()) {

            surfingScore = (entry.getValue().getWindSpeed() * 3) + entry.getValue().getTemperature();
            mapOfValuesFromFormula.put(entry.getKey(), surfingScore);

        }

        return mapOfValuesFromFormula;

    }

    private Calculation() {
    }
}
