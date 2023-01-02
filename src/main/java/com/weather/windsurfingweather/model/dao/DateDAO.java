package com.weather.windsurfingweather.model.dao;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class DateDAO {
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
}
