package com.weather.windsurfingweather.model.record;

public record LocationRecord(String locationName, Long temperature, Long windSpeed, GeographicalCoordinatesRecord coordinates) {
}
