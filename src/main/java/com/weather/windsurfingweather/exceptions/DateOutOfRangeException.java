package com.weather.windsurfingweather.exceptions;

import org.springframework.http.HttpStatus;

public class DateOutOfRangeException extends GlobalException {
    //jakbys uzyl do tego i w innych exceptionach  i18n to juz calkowicie klasa :)
    public DateOutOfRangeException() {
        super(HttpStatus.NOT_ACCEPTABLE, "Given date is out of 16 days range");
    }
}
