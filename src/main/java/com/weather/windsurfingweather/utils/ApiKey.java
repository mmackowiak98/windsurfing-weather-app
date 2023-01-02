package com.weather.windsurfingweather.utils;

import lombok.Getter;

@Getter
public class ApiKey {

    private static final String apiKey = "f749629ee6c24a8e832a06773bcfeaf4";

    public static String getKey() {
        return apiKey;
    }

    private ApiKey() {
    }
}

