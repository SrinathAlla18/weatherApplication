package com.srinath.weather.controller;
import com.srinath.weather.DTO.ForecastResponse;
import com.srinath.weather.DTO.HourlyResponse;
import com.srinath.weather.DTO.weatherResponse2;
import com.srinath.weather.service.ForecastService;
import com.srinath.weather.service.weatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")// Allow requests from React running on localhost:3000
@Slf4j
public class WeatherController {
    @Autowired
    private weatherService weatherService;
    @Autowired
    private ForecastService forecastService;

    @GetMapping(path="/{location}")
    public weatherResponse2 dailyWeather(@PathVariable(value = "location",required = true) String location) {
         return weatherService.dailyService(location);
    }
    @GetMapping("hourly/{location}")
    public List<HourlyResponse.HourlyData> hourlyWeather(@PathVariable(value = "location",required = true) String location){
         return weatherService.hourlyService(location);
    }
    @GetMapping("forecast/{location}")
    public ForecastResponse.LocationData forecastController(@PathVariable(value = "location",required = true) String location){
        return forecastService.LocationData(location);
    }



}
