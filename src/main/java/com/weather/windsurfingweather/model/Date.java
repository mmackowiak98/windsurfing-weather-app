package com.weather.windsurfingweather.model;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class Date {
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
}
