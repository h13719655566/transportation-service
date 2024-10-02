package com.hdalgorithm.transportation_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class GoogleMapsService {

    @Value("${google.maps.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getApiKey() {
        return apiKey;
    }

    public JsonNode getDirections(String origin, String destination, String mode) {
        log.info("Getting directions: origin={}, destination={}, mode={}", origin, destination, mode);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/directions/json")
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("mode", mode)
                .queryParam("key", apiKey)
                .build()
                .toUri();

        log.debug("Calling Google Maps API with URL: {}", uri);

        try {
            JsonNode response = restTemplate.getForObject(uri, JsonNode.class);
            log.debug("Received response from Google Maps API: {}", response);

            if (response != null && "OK".equals(response.path("status").asText())) {
                return response;
            } else {
                log.warn("No route found or error in API response. Status: {}",
                        response != null ? response.path("status").asText() : "null");
                return null;
            }
        } catch (Exception e) {
            log.error("Error getting directions", e);
            throw new RuntimeException("Failed to fetch directions", e);
        }
    }

    public String getGoogleMapsApiUrl() {
        return "https://maps.googleapis.com/maps/api/js?key=" + apiKey;
    }
}