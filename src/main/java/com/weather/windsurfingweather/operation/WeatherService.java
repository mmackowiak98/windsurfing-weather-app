package com.weather.windsurfingweather.operation;

import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.record.LocationRecord;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

public interface WeatherService {

    LocationRecord getLocation(LocalDate date) throws IOException;

    Location addNewLocation(String locationUrl) throws IOException;

    Collection<Location> getAll();
}
