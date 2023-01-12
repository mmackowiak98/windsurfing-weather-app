package com.weather.windsurfingweather.model;

import lombok.Getter;

@Getter
//uzywaj lombooka, poczytaj o @Data i @Value
public class GeographicalCoordinates {
    private String lon;
    private String lat;

    public GeographicalCoordinates(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }
}
