package com.weather.windsurfingweather.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
//albo constructor w kazdym exception albo @AllArgsContructor, kod musi byc spojny inaczej wyglada jakby pisala go grupka zebrana z ulicy
public class GlobalException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}
