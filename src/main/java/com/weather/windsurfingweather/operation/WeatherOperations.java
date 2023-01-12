package com.weather.windsurfingweather.operation;

import com.weather.windsurfingweather.model.Date;
import com.weather.windsurfingweather.model.entity.Location;
import com.weather.windsurfingweather.model.Url;
import com.weather.windsurfingweather.model.record.LocationRecord;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RequestMapping("/default")
public interface WeatherOperations {

    @GetMapping("/best")
    @ResponseStatus(HttpStatus.OK)
    LocationRecord getBestLocation(@Valid @RequestBody Date date) throws IOException;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Location addNewLocation(@RequestBody Url locationUrl) throws IOException;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    Collection<Location> getAllLocations();
}
