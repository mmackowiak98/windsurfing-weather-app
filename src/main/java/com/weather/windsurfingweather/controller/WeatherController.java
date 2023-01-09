package com.weather.windsurfingweather.controller;

import com.weather.windsurfingweather.model.Location;
import com.weather.windsurfingweather.model.dto.DateDTO;
import com.weather.windsurfingweather.model.dao.LocationDAO;
import com.weather.windsurfingweather.model.dto.UrlDTO;
import com.weather.windsurfingweather.service.WeatherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private WeatherService weatherService;

    @GetMapping("/best")
    public LocationDAO getBestLocation(@Valid @RequestBody DateDTO dto) throws IOException {
        return weatherService.getBestLocation(dto.getDate());
    }

    @PostMapping()
    public Location addNewLocation(@RequestBody UrlDTO locationUrl) throws IOException {
        return weatherService.addNewLocation(locationUrl.getUrl());
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Location>> getAllLocations() {
        Collection<Location> all = weatherService.getAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
                .body(all);
    }
}
