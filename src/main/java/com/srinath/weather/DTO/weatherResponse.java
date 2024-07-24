package com.srinath.weather.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class weatherResponse {
    private String resolvedAddress;
    private float tempMax;
    private float tempMin;
    private float temperature;
    private float feelsLikeMax;
    private float feelsLikeMin;
    private float feelsLike;
    private float precipitation;
    private float windSpeed;
    private String description;
    private String conditions;


}
