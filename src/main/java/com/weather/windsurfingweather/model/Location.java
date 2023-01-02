package com.weather.windsurfingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String url;
    private String locationName;
    private String lon;
    private String lat;

    public Location(String url, String locationName, String lon, String lat) {
        this.url = url;
        this.locationName = locationName;
        this.lon = lon;
        this.lat = lat;
    }
}
