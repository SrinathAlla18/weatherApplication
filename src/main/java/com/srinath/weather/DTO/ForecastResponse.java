package com.srinath.weather.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    @JsonProperty("locations")
    private Map<String, LocationData> locations;
//    private List<LocationData> locations;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LocationData{
        private String address;
        private String id;
        @JsonProperty("values")
        private List<WeatherValue> values;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherValue {
        private String datetimeStr;
        private Double precip;
        private Double solarradiation;
        private Double dew;
        private Double humidity;
        private Double temp;
        private Double maxt;
        private Double visibility;
        private String wgust;
        private String conditions;
    }

}
