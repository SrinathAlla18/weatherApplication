package com.srinath.weather.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srinath.weather.DTO.ForecastResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class ForecastService {
    @Value("${weather.api.key}")
    private String apiKey;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

//    @CircuitBreaker(name ="forecastService",fallbackMethod = "redisHealthFallback")
//    public static boolean isRedisAvailable(){
//        try {
//             RedisTemplate<String,Object> redisTemplate =new RedisTemplate<>();
//            return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.ping() != null));
//        } catch (Exception e) {
//            log.error("Redis health check failed: {}", e.getMessage());
//            throw e; // Let the circuit breaker handle the exception
//        }
//    }
//    // Fallback method
//    public boolean redisHealthFallback(Exception e) {
//        log.warn("Fallback triggered for Redis health check due to: {}", e.getMessage());
//        return false; // Assume Redis is unavailable
//    }

    @Cacheable(value = "forecastData", key = "#location", unless = "#result==null")
    public ForecastResponse.LocationData LocationData(String location) {
        String API_uri = "https://weather.visualcrossing.com" +
                "/VisualCrossingWebServices/rest/services/weatherdata/forecast?" +
                "location=" + location + "&aggregateHours=24&unitGroup=us" +
                "&shortColumnNames=false&contentType=json&key=" + apiKey;
        try {
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "*/*");
            // Create HTTP entity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> jsonResponse = restTemplate.exchange(API_uri, HttpMethod.GET,
                    entity,
                    String.class);

            if (jsonResponse.getBody() != null) {
                // Convert raw JSON to DTO
//                ObjectMapper mapper = new ObjectMapper();
                ForecastResponse forecastResponse = mapper.readValue(jsonResponse.getBody(), ForecastResponse.class);

                // Get location data from the response
                if (forecastResponse != null && forecastResponse.getLocations() != null) {
                    ForecastResponse.LocationData locationData = forecastResponse.getLocations().get(location);
                    if (locationData != null) {
                        log.info("Cache MISS for location: {}, in Main Method", location);
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

        } catch (RestClientException e) {
            log.error("Rest client error while calling the weather API:{} ", e.getMessage());
            return new ForecastResponse.LocationData();
        } catch (Exception e) {
            log.error("An unexpected error occurred: ", e);
            return new ForecastResponse.LocationData();
        }
        return new ForecastResponse.LocationData();

    }
}
