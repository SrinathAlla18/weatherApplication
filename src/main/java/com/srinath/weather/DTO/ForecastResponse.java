package com.srinath.weather.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("locations")
    private Map<String, LocationData> locations;
//    private List<LocationData> locations;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LocationData implements Serializable{
        @Serial
        private static final long serialVersionUID = 2L;

        private String address;
        private String id;
        @JsonProperty("values")
        private List<WeatherValue> values;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherValue implements Serializable {
        @Serial
        private static final long serialVersionUID = 3L;

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
