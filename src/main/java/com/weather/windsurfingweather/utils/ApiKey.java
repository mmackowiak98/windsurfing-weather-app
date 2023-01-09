package com.weather.windsurfingweather.utils;

import lombok.Getter;

@Getter
//getter z lomboka i metoda?
//klasa do wywalenia przenies zmienna do serwisu bezposrednio albo utworz klase ktora bedzie miala wszystkie zmienne potrzebne do programu (najlepiej z uzyciem application properties
public class ApiKey {

    //pole nie powinno miec takiej samej nazwy jak klasa
    private static final String apiKey = "f749629ee6c24a8e832a06773bcfeaf4";

    public static String getKey() {
        return apiKey;
    }

    //czyscimy po sobie ;)

    private ApiKey() {
    }
}

