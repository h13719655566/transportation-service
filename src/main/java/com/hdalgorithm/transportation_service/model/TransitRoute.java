package com.hdalgorithm.transportation_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransitRoute {
    private String origin;
    private String destination;
    private String duration;
    private String distance;
    private List<String> steps;
    private String estimatedArrivalTime;
    private String overview;
    private List<TransitSegment> segments;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransitSegment {
        private String type; // e.g., "WALK", "BUS", "TRAIN"
        private String instruction;
        private String duration;
        private String distance;
    }
}