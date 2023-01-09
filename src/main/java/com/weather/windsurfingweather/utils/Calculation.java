package com.weather.windsurfingweather.utils;

import com.weather.windsurfingweather.model.record.LocationRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculation {
    private static final int MIN_WIND_SPEED = 5;
    private static final int MAX_WIND_SPEED = 18;
    private static final int MIN_TEMPERATURE = 5;
    private static final int MAX_TEMPERATURE = 35;

    public static Map<String, LocationRecord> filterByRequirements(Map<String, LocationRecord> locations) {

        return locations.entrySet().stream()
            //nie lepiej zrobic pomocnicza metode ktora ma 3 parametry checkIfValueInRange(value, min,max) i wywolac ja dwa razy z .filter?
                .filter(o -> o.getValue().windSpeed() > MIN_WIND_SPEED)
                .filter(o -> o.getValue().windSpeed() < MAX_WIND_SPEED)
                .filter(o -> o.getValue().temperature() > MIN_TEMPERATURE)
                .filter(o -> o.getValue().temperature() < MAX_TEMPERATURE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, Long> calculateValueFromFormula(Map<String, LocationRecord> locations) {

        Map<String, Long> mapOfValuesFromFormula = new HashMap<>();
// nie potrzebujesz tej zmiennej poza petla
        Long surfingScore;

        for (Map.Entry<String, LocationRecord> entry : locations.entrySet()) {

            surfingScore = (entry.getValue().windSpeed() * 3) + entry.getValue().temperature();
            mapOfValuesFromFormula.put(entry.getKey(), surfingScore);

        }

        return mapOfValuesFromFormula;

    }

    private Calculation() {
    }
}
