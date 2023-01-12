package com.weather.windsurfingweather.controller;

import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.Date;
import com.weather.windsurfingweather.model.record.LocationRecord;
import com.weather.windsurfingweather.model.Url;
import com.weather.windsurfingweather.operation.WeatherOperations;
import com.weather.windsurfingweather.service.WeatherServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;


@RestController
@RequestMapping("/api/weather")
public class WeatherController implements WeatherOperations {

    private final WeatherServiceImpl weatherService;

    public WeatherController(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public LocationRecord getBestLocation(@Valid @RequestBody Date date) throws IOException {
        return weatherService.getLocation(date.getDate());
    }

    @Override
    public Location addNewLocation(@Valid @RequestBody Url locationUrl) throws IOException {
        return weatherService.addNewLocation(locationUrl.getUrl());
    }

    @Override
    public Collection<Location> getAllLocations() {
        return weatherService.getAll();
    }
}
