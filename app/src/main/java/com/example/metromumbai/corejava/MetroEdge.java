package com.example.metromumbai.corejava;

/**
 * Represents a directed Edge between two stations in the metro network graph.
 * Supports weighted journey calculations (time in minutes and distance in km).
 */
public class MetroEdge {
    private final String fromStationId;
    private final String toStationId;
    private final String lineId;
    private final boolean isInterchange;
    private final double travelTimeMinutes;
    private final double distanceKm;
    private final String description;

    public MetroEdge(String fromStationId, String toStationId, String lineId,
                     boolean isInterchange, double travelTimeMinutes, double distanceKm,
                     String description) {
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.lineId = lineId;
        this.isInterchange = isInterchange;
        this.travelTimeMinutes = travelTimeMinutes;
        this.distanceKm = distanceKm;
        this.description = description != null ? description : "";
    }

    public String getFromStationId() {
        return fromStationId;
    }

    public String getToStationId() {
        return toStationId;
    }

    public String getLineId() {
        return lineId;
    }

    public boolean isInterchange() {
        return isInterchange;
    }

    public double getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return fromStationId + " -> " + toStationId + " [" + lineId + ", " + travelTimeMinutes + " min]";
    }
}
