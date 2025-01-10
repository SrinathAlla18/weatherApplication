package com.srinath.weather.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srinath.weather.DTO.ForecastResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class ForecastService {
    @Value("${weather.api.key}")
    private String apiKey;


    @Cacheable(value = "forecastData",key = "#location")
    public ForecastResponse.LocationData forecastData(String location){

        String API_uri= "https://weather.visualcrossing.com" +
                "/VisualCrossingWebServices/rest/services/weatherdata/forecast?" +
                "location="+location+"&aggregateHours=24&unitGroup=us" +
                "&shortColumnNames=false&contentType=json&key="+apiKey;
        try{
            RestTemplate restTemplate = new RestTemplate();
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "*/*");
            // Create HTTP entity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> jsonResponse= restTemplate.exchange(API_uri, HttpMethod.GET,
                    entity,
                    String.class);

            if (jsonResponse.getBody() != null) {
                // Convert raw JSON to DTO
                ObjectMapper mapper = new ObjectMapper();
                ForecastResponse forecastResponse = mapper.readValue(jsonResponse.getBody(), ForecastResponse.class);

                // Get location data from the response
                if (forecastResponse != null && forecastResponse.getLocations() != null) {
                    ForecastResponse.LocationData locationData = forecastResponse.getLocations().get(location);
                    if (locationData != null) {
                        return locationData;
                    } else {
                        log.error("Location data not found for location: {}", location);
                    }
                } else {
                    log.error("Invalid response structure from weather API");
                }
            } else {
                log.error("Received null response body from weather API");
            }

        }catch(RestClientException e){
            log.error("Rest client error while calling the weather API:{} ", e.getMessage());
            return new ForecastResponse.LocationData();
        }catch (Exception e){
            log.error("An unexpected error occurred: ", e);
            return new ForecastResponse.LocationData();
        }
        return new ForecastResponse.LocationData();
    }
}
