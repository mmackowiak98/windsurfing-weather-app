package com.weather.windsurfingweather.model.record;

import com.weather.windsurfingweather.model.GeographicalCoordinates;
import lombok.Getter;
import lombok.ToString;

public record LocationRecord(String locationName, Long temperature, Long windSpeed, GeographicalCoordinates coordinates) {
}
