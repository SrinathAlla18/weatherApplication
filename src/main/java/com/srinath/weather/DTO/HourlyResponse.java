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
public class HourlyResponse {
    @JsonProperty("resolvedAddress")
    private String resolvedAddress;
    @JsonProperty("days")
    private List<DaysWeather> Weather;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DaysWeather{
        @JsonProperty("datetime")
        private String datetime;
        private List<HourlyData> hours;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HourlyData{
        private String datetime;
        private double temp;
        private double feelslike;
        private double humidity;
        private double dew;
        private double precip;
        private double snow;
        private double windspeed;
        private double winddir;
        private double pressure;
        private double visibility;
        private double cloudcover;
        private double solarradiation;
        private double uvindex;
        private String conditions;
        private String icon;
    }

}

