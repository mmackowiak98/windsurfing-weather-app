package com.weather.windsurfingweather.model.dao;

import com.weather.windsurfingweather.model.GeographicalCoordinates;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LocationDAO {
    private final String locationName;
    private final Long temperature;
    private final Long windSpeed;

    private final GeographicalCoordinates coordinates;

    public LocationDAO(String locationName, Long temperature, Long windSpeed, GeographicalCoordinates coordinates) {
        this.locationName = locationName;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.coordinates = coordinates;
    }
}
