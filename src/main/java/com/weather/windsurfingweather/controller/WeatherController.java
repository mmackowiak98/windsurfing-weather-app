package com.weather.windsurfingweather.controller;

import com.weather.windsurfingweather.model.Location;
import com.weather.windsurfingweather.model.dao.DateDAO;
import com.weather.windsurfingweather.model.dao.LocationDAO;
import com.weather.windsurfingweather.model.dto.UrlDTO;
import com.weather.windsurfingweather.service.WeatherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private WeatherService weatherService;

    @GetMapping("/best")
    public LocationDAO getBestLocation(@Valid @RequestBody DateDAO dao) throws IOException {
        return weatherService.getBestLocation(dao.getDate());
    }

    @PostMapping()
    public Location addNewLocation(@RequestBody UrlDTO locationUrl) throws IOException {
        return weatherService.addNewLocation(locationUrl.getUrl());
    }

    @GetMapping("/all")
    public Collection<Location> getAllLocations() {
        return weatherService.getAll();
    }
}