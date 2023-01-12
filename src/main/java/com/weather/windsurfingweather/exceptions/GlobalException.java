package com.weather.windsurfingweather.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public GlobalException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
