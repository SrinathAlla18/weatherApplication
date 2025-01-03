package com.srinath.weather.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srinath.weather.DTO.HourlyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.srinath.weather.DTO.weatherResponse2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class weatherService {


    public weatherResponse2 dailyService(String location)  {
        final Logger logger = LoggerFactory.getLogger(weatherService.class);
        String WEATHER_API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location+"?key=QXAE98FVU5MWGEA5VSCUTYGVA";

        try{
            RestTemplate restTemplate = new RestTemplate();
            weatherResponse2 jsonResponse = restTemplate.getForObject(WEATHER_API_URL, weatherResponse2.class);
            weatherResponse2.DayWeather firstDayWeather = jsonResponse.getDaysweather().get(0);
            return new weatherResponse2(jsonResponse.getResolvedAddress(), List.of(firstDayWeather));
        }catch (RestClientException e) {
            logger.error("Rest client error while calling the weather API:{} ", e.getMessage());
            return new weatherResponse2("Rest client error occurred",List.of());
        }catch (Exception e){
            logger.error("An unexpected error occurred: ", e);
            return new weatherResponse2("An unexpected error occurred", List.of());
        }
    }
    public List<HourlyResponse.HourlyData> hourlyService(String location) {
        String WEATHER_API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location+"?key=QXAE98FVU5MWGEA5VSCUTYGVA";
        final Logger logger = LoggerFactory.getLogger(weatherService.class);
        try{
            RestTemplate restTemplate = new RestTemplate();
            HourlyResponse jsonResponse = restTemplate.getForObject(WEATHER_API_URL, HourlyResponse.class);
            HourlyResponse.DaysWeather firstDayWeather = jsonResponse.getWeather().get(0);
            return firstDayWeather.getHours();
        }catch (RestClientException e) {
            logger.error("Rest client error while calling the weather API:{} ", e.getMessage());
            return List.of();
        }catch (Exception e){
            logger.error("An unexpected error occurred: ", e);
            return List.of();
        }

    }
}
