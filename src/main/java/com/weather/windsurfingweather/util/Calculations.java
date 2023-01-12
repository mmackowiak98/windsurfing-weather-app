package com.weather.windsurfingweather.util;

import com.weather.windsurfingweather.exceptions.NoForecastFoundException;
import com.weather.windsurfingweather.model.record.LocationRecord;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Calculations {

    private final int minWindSpeed;
    private final int maxWindSpeed;
    private final int minTemperature;
    private final int maxTemperature;

    public Calculations(@Value("${properties.min-wind-speed}") int minWindSpeed, @Value("${properties.max-wind-speed}") int maxWindSpeed, @Value("${properties.min-temperature}") int minTemperature, @Value("${properties.max-temperature}") int maxTemperature) {
        this.minWindSpeed = minWindSpeed;
        this.maxWindSpeed = maxWindSpeed;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }


    public Map<String, LocationRecord> filterByRequirements(Map<String, LocationRecord> locations) {
        if (locations != null) {

            return locations.entrySet().stream()
                    .filter(o -> checkIfValueInRange(o.getValue().windSpeed(), minWindSpeed, maxWindSpeed))
                    .filter(o -> checkIfValueInRange(o.getValue().temperature(), minTemperature, maxTemperature))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return Collections.emptyMap();
        }
    }


    public Map<String, Long> calculateValueFromFormula(Map<String, LocationRecord> locations) {
        if (locations != null) {

            Map<String, Long> mapOfValuesFromFormula = new HashMap<>();

            for (Map.Entry<String, LocationRecord> entry : locations.entrySet()) {

                Long surfingScore = (entry.getValue().windSpeed() * 3) + entry.getValue().temperature();
                mapOfValuesFromFormula.put(entry.getKey(), surfingScore);

            }

            return mapOfValuesFromFormula;
        } else {
            return Collections.emptyMap();
        }

    }

    private boolean checkIfValueInRange(double value, double min, double max) {
        return value > min && value < max;
    }

}
