package com.weather.windsurfingweather.model;

import lombok.Getter;

@Getter
public class GeographicalCoordinates {
    private String lon;
    private String lat;

    public GeographicalCoordinates(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }
}
