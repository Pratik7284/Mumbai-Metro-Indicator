package com.example.metromumbai.corejava;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Vertex in the Mumbai Metro Graph.
 * Designed for MCA Advanced Data Structures demonstration.
 */
public class MetroStation {
    private final String stationId;
    private final String name;
    private final String lineId;
    private final String lineDisplayName;
    private final String lineColorHex;
    private final double latitude;
    private final double longitude;
    private final boolean isInterchange;
    private final List<String> connectedLineIds;
    private final String interchangeNote;
    private final int platformCount;
    private final String exitNote;
    private final String zone;

    public MetroStation(String stationId, String name, String lineId, String lineDisplayName,
                        String lineColorHex, double latitude, double longitude, boolean isInterchange,
                        List<String> connectedLineIds, String interchangeNote, int platformCount,
                        String exitNote, String zone) {
        this.stationId = stationId;
        this.name = name;
        this.lineId = lineId;
        this.lineDisplayName = lineDisplayName;
        this.lineColorHex = lineColorHex;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isInterchange = isInterchange;
        this.connectedLineIds = connectedLineIds != null ? connectedLineIds : new ArrayList<String>();
        this.interchangeNote = interchangeNote != null ? interchangeNote : "";
        this.platformCount = platformCount;
        this.exitNote = exitNote != null ? exitNote : "";
        this.zone = zone != null ? zone : "Zone A";
    }

    public String getStationId() {
        return stationId;
    }

    public String getName() {
        return name;
    }

    public String getLineId() {
        return lineId;
    }

    public String getLineDisplayName() {
        return lineDisplayName;
    }

    public String getLineColorHex() {
        return lineColorHex;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isInterchange() {
        return isInterchange;
    }

    public List<String> getConnectedLineIds() {
        return connectedLineIds;
    }

    public String getInterchangeNote() {
        return interchangeNote;
    }

    public int getPlatformCount() {
        return platformCount;
    }

    public String getExitNote() {
        return exitNote;
    }

    public String getZone() {
        return zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroStation that = (MetroStation) o;
        return Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId);
    }

    @Override
    public String toString() {
        return name + " (" + lineDisplayName + ")";
    }
}
