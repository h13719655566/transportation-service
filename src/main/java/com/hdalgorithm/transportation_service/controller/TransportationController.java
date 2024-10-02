package com.hdalgorithm.transportation_service.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.hdalgorithm.transportation_service.service.GoogleMapsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transportation")
@CrossOrigin
public class TransportationController {

    private final GoogleMapsService googleMapsService;

    @GetMapping("/directions")
    public ResponseEntity<?> getDirections(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam(defaultValue = "driving") String mode) {
        log.info("Received request for directions: origin={}, destination={}, mode={}", origin, destination, mode);
        try {
            JsonNode route = googleMapsService.getDirections(origin, destination, mode);
            if (route == null) {
                log.warn("No route found for origin={}, destination={}, mode={}", origin, destination, mode);
                return ResponseEntity.status(404).body(Map.of("error", "No route found"));
            }
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Error while fetching directions", e);
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred while fetching directions: " + e.getMessage()));
        }
    }

    @GetMapping("/google-maps-api-url")
    public ResponseEntity<String> getGoogleMapsApiUrl() {
        log.info("Received request for Google Maps API URL");
        try {
            String url = googleMapsService.getGoogleMapsApiUrl();
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            log.error("Error while getting Google Maps API URL", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/google-maps-script")
    public ResponseEntity<String> getGoogleMapsScript() {
        String apiKey = googleMapsService.getApiKey();
        String scriptUrl = String.format(
                "https://maps.googleapis.com/maps/api/js?key=%s&libraries=places&callback=initMaps",
                apiKey
        );
        return ResponseEntity.ok(scriptUrl);
    }
}