package com.srinath.weather.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.srinath.weather.DTO.weatherResponse;
import com.srinath.weather.DTO.weatherResponse2;
import com.srinath.weather.service.weatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")  // Allow requests from React running on localhost:3000

public class WeatherController {
    @Autowired
    private weatherService weatherService;
    @GetMapping("/{location}")
    public weatherResponse2 weatherTimeline(@PathVariable("location") String location) throws JsonProcessingException {
         return weatherService.weatherTimeline(location);

    }

}
