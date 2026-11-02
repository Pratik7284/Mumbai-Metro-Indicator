package com.example.metromumbai.corejava;

import java.util.ArrayList;
import java.util.List;

/**
 * Result returned by BFS or Dijkstra route calculation.
 * Encapsulates full reconstructed path and transit analytics.
 */
public class RouteResult {
    private final String routeId;
    private final MetroStation origin;
    private final MetroStation destination;
    private final List<MetroStation> stationSequence;
    private final List<RouteStep> steps;
    private final List<String> lineSequence;
    private final List<MetroStation> interchangeStations;
    private final int totalStops;
    private final int interchangeCount;
    private final double totalTimeMinutes;
    private final double totalDistanceKm;
    private final int fareInr;
    private final int smartCardFareInr;
    private final String algorithmUsed;
    private final boolean isRecommended;
    private final String summaryTitle;

    public RouteResult(String routeId, MetroStation origin, MetroStation destination,
                       List<MetroStation> stationSequence, List<RouteStep> steps,
                       List<String> lineSequence, List<MetroStation> interchangeStations,
                       int totalStops, int interchangeCount, double totalTimeMinutes,
                       double totalDistanceKm, int fareInr, String algorithmUsed,
                       boolean isRecommended, String summaryTitle) {
        this.routeId = routeId;
        this.origin = origin;
        this.destination = destination;
        this.stationSequence = stationSequence != null ? stationSequence : new ArrayList<MetroStation>();
        this.steps = steps != null ? steps : new ArrayList<RouteStep>();
        this.lineSequence = lineSequence != null ? lineSequence : new ArrayList<String>();
        this.interchangeStations = interchangeStations != null ? interchangeStations : new ArrayList<MetroStation>();
        this.totalStops = totalStops;
        this.interchangeCount = interchangeCount;
        this.totalTimeMinutes = totalTimeMinutes;
        this.totalDistanceKm = totalDistanceKm;
        this.fareInr = fareInr;
        this.smartCardFareInr = (int) Math.round(fareInr * 0.90); // 10% discount on NCMC
        this.algorithmUsed = algorithmUsed;
        this.isRecommended = isRecommended;
        this.summaryTitle = summaryTitle;
    }

    public String getRouteId() {
        return routeId;
    }

    public MetroStation getOrigin() {
        return origin;
    }

    public MetroStation getDestination() {
        return destination;
    }

    public List<MetroStation> getStationSequence() {
        return stationSequence;
    }

    public List<RouteStep> getSteps() {
        return steps;
    }

    public List<String> getLineSequence() {
        return lineSequence;
    }

    public List<MetroStation> getInterchangeStations() {
        return interchangeStations;
    }

    public int getTotalStops() {
        return totalStops;
    }

    public int getInterchangeCount() {
        return interchangeCount;
    }

    public double getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public int getRoundedTimeMinutes() {
        return (int) Math.round(totalTimeMinutes);
    }

    public double getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public int getFareInr() {
        return fareInr;
    }

    public int getSmartCardFareInr() {
        return smartCardFareInr;
    }

    public String getAlgorithmUsed() {
        return algorithmUsed;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public String getSummaryTitle() {
        return summaryTitle;
    }
}
