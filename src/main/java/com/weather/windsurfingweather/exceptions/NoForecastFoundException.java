package com.weather.windsurfingweather.exceptions;

import org.springframework.http.HttpStatus;

public class NoForecastFoundException extends GlobalException {
    public NoForecastFoundException() {
        super(HttpStatus.NOT_FOUND, "Forecast not found");
    }
}
