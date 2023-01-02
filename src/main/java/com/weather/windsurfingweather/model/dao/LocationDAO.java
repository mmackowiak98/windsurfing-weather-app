package com.weather.windsurfingweather.model.dao;

import com.weather.windsurfingweather.model.GeographicalCoordinates;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LocationDAO {
    private String locationName;
    private Long temperature;
    private Long windSpeed;

    private GeographicalCoordinates coordinates;

    public LocationDAO(String locationName, Long temperature, Long windSpeed, GeographicalCoordinates coordinates) {
        this.locationName = locationName;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.coordinates = coordinates;
    }
}
