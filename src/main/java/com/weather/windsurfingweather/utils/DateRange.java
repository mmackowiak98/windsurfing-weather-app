package com.weather.windsurfingweather.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class DateRange {
    private static final Long ALLOWED_RANGE = 15L;

    public static boolean isInRange(LocalDate givenDate){
        return givenDate.isBefore(LocalDate.now().plusDays(ALLOWED_RANGE));
    }

    private DateRange() {
    }
}
