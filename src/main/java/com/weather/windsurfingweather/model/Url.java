package com.weather.windsurfingweather.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Url {
    @NotNull
    String url;
}
