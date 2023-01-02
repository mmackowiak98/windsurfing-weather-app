package com.weather.windsurfingweather.exceptions;

import org.springframework.http.HttpStatus;

public class LocationAlreadyExistsException extends GlobalException{
    public LocationAlreadyExistsException() {
        super(HttpStatus.NOT_ACCEPTABLE, "This location already exists in database");
    }
}
