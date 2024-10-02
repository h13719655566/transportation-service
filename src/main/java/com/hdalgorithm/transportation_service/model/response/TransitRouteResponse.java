package com.hdalgorithm.transportation_service.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransitRouteResponse {
    private String origin;
    private String destination;
    private String duration;
    private String distance;
    private List<String> steps;
}
