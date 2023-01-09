package com.weather.windsurfingweather.controller;

import com.weather.windsurfingweather.model.Location;
import com.weather.windsurfingweather.model.dto.DateDTO;
import com.weather.windsurfingweather.model.record.LocationRecord;
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
//warto zrobic interfejs WeatherApi ktory bedzie  mial informacje o endpointach i kody odpowiedzi jakie appka moze zwrocic a w Controllerze sama implementacja metod
public class WeatherController {

    private WeatherService weatherService;

    @GetMapping("/best")
    //dto jest uzywane dla poziomu serwisu a nie controllera
    public LocationRecord getBestLocation(@Valid @RequestBody DateDTO dto) throws IOException {
        return weatherService.getBestLocation(dto.getDate());
    }

    @PostMapping()
    //dto jest uzywane dla poziomu serwisu a nie controllera
    public Location addNewLocation(@RequestBody UrlDTO locationUrl) throws IOException {
        return weatherService.addNewLocation(locationUrl.getUrl());
    }

    @GetMapping("/all")
    //jedna z roznic @RestController a @Controller to wlasnie opakowanie w Response Entity i nie musimy pisac tego wprost w typie zwracanym metody
    public ResponseEntity<Collection<Location>> getAllLocations() {
        Collection<Location> all = weatherService.getAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
                .body(all);
    }
}
