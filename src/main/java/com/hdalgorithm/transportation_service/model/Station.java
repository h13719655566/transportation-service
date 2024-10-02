package com.hdalgorithm.transportation_service.model;

import lombok.Value;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Value
public class Station {
    @JsonProperty("name")
    String name;

    @JsonProperty("latitude")
    double latitude;

    @JsonProperty("longitude")
    double longitude;

    @JsonProperty("types")
    List<String> types;

    @JsonProperty("estimatedArrivalTime")
    String estimatedArrivalTime;

    @JsonProperty("overview")
    String overview;
}