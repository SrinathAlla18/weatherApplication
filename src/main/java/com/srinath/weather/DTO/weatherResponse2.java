package com.srinath.weather.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class weatherResponse2 {
    @JsonProperty("resolvedAddress")
    private String resolvedAddress;
    @JsonProperty("days")
    private List<DayWeather> daysweather;
    // Nested class representing the day's weather data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
     public static class DayWeather {

        @JsonProperty("tempmax")
        private float tempMax;

        @JsonProperty("tempmin")
        private float tempMin;

        @JsonProperty("temp")
        private float temperature;

        @JsonProperty("feelslikemax")
        private float feelsLikeMax;

        @JsonProperty("feelslikemin")
        private float feelsLikeMin;

        @JsonProperty("feelslike")
        private float feelsLike;

        @JsonProperty("precip")
        private float precipitation;

        @JsonProperty("windspeed")
        private float windSpeed;

        @JsonProperty("description")
        private String description;

        @JsonProperty("conditions")
        private String conditions;



    }}
