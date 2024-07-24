package com.srinath.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srinath.weather.DTO.weatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class weatherService {
    @Autowired
    private ObjectMapper objectMapper;

    public weatherResponse weatherTimeline(String location) throws JsonProcessingException {
        String WEATHER_API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location+"/2020-10-01/2020-10-01?key=QXAE98FVU5MWGEA5VSCUTYGVA";
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(WEATHER_API_URL, String.class);

        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        String resolvedAddress = rootNode.path("resolvedAddress").asText();
        JsonNode dayNode = rootNode.path("days").get(0);
        float tempMax = dayNode.path("tempmax").floatValue();
        float tempMin = dayNode.path("tempmin").floatValue();
        float temperature = dayNode.path("temp").floatValue();
        float feelsLikeMax = dayNode.path("feelslikemax").floatValue();
        float feelsLikeMin = dayNode.path("feelslikemin").floatValue();
        float feelsLike = dayNode.path("feelslike").floatValue();
        float precipitation = dayNode.path("precip").floatValue();
        float windSpeed= dayNode.path("windspped").floatValue();
        String description=dayNode.path("description").asText();
        String conditions=dayNode.path("conditions").asText();


        return new weatherResponse(resolvedAddress, tempMax, tempMin, temperature, feelsLikeMax, feelsLikeMin,feelsLike,precipitation,windSpeed,description,conditions);





    }

}
