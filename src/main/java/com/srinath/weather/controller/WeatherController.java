package com.srinath.weather.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.srinath.weather.DTO.weatherResponse;
import com.srinath.weather.service.weatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WeatherController {
    @Autowired
    private weatherService weatherService;
    @GetMapping("/{location}")
    public weatherResponse weatherTimeline(@PathVariable("location") String location) throws JsonProcessingException {
         return weatherService.weatherTimeline(location);

    }

}
