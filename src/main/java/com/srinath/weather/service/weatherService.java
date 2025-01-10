package com.srinath.weather.service;
import com.srinath.weather.DTO.HourlyResponse;
import lombok.extern.slf4j.Slf4j;
import com.srinath.weather.DTO.weatherResponse2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class weatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Cacheable(value = "dailyData",key = "#location")
    public weatherResponse2 dailyService(String location)  {
        String WEATHER_API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location+"?key="+apiKey;

        try{
            RestTemplate restTemplate = new RestTemplate();
            weatherResponse2 jsonResponse = restTemplate.getForObject(WEATHER_API_URL, weatherResponse2.class);
            weatherResponse2.DayWeather firstDayWeather = jsonResponse.getDaysweather().get(0);
            return new weatherResponse2(jsonResponse.getResolvedAddress(), List.of(firstDayWeather));
        }catch (RestClientException e) {
            log.error("Rest client error while calling the weather API:{} ", e.getMessage());
            return new weatherResponse2("Rest client error occurred",List.of());
        }catch (Exception e){
            log.error("An unexpected error occurred: ", e);
            return new weatherResponse2("An unexpected error occurred", List.of());
        }
    }
    @Cacheable(value = "hourlyData",key = "#location")
    public List<HourlyResponse.HourlyData> hourlyService(String location) {
        String WEATHER_API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location+"?key="+apiKey;
        try{
            RestTemplate restTemplate = new RestTemplate();
            HourlyResponse jsonResponse = restTemplate.getForObject(WEATHER_API_URL, HourlyResponse.class);
            HourlyResponse.DaysWeather firstDayWeather = jsonResponse.getWeather().get(0);
            return firstDayWeather.getHours();
        }catch (RestClientException e) {
            log.error("Rest client error while calling the weather API:{} ", e.getMessage());
            return List.of();
        }catch (Exception e){
            log.error("An unexpected error occurred: ", e);
            return List.of();
        }

    }
}
