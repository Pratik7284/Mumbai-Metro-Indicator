package com.example.metromumbai.corejava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adjacency-list graph model representing the Mumbai Metro railway network.
 * Core class for MCA Advanced Data Structures.
 *
 * Space Complexity: O(V + E) where V is station nodes and E is track segments.
 */
public class MetroGraph {
    private final Map<String, MetroStation> stations;
    private final Map<String, List<MetroEdge>> adjacencyList;

    public MetroGraph() {
        this.stations = new HashMap<String, MetroStation>();
        this.adjacencyList = new HashMap<String, List<MetroEdge>>();
    }

    /**
     * Adds a metro station vertex to the network.
     * Time Complexity: O(1)
     */
    public void addStation(MetroStation station) {
        if (station == null || station.getStationId() == null) return;
        stations.put(station.getStationId(), station);
        if (!adjacencyList.containsKey(station.getStationId())) {
            adjacencyList.put(station.getStationId(), new ArrayList<MetroEdge>());
        }
    }

    /**
     * Adds a directed track segment edge between two stations.
     * Time Complexity: O(1)
     */
    public void addEdge(MetroEdge edge) {
        if (edge == null) return;
        String fromId = edge.getFromStationId();
        if (!adjacencyList.containsKey(fromId)) {
            adjacencyList.put(fromId, new ArrayList<MetroEdge>());
        }
        adjacencyList.get(fromId).add(edge);
    }

    /**
     * Convenience method to add bidirectional corridor connections.
     */
    public void addBidirectionalEdge(String stationA, String stationB, String lineId,
                                     boolean isInterchange, double timeMinutes, double distanceKm,
                                     String desc) {
        addEdge(new MetroEdge(stationA, stationB, lineId, isInterchange, timeMinutes, distanceKm, desc));
        addEdge(new MetroEdge(stationB, stationA, lineId, isInterchange, timeMinutes, distanceKm, desc));
    }

    public MetroStation getStation(String stationId) {
        return stations.get(stationId);
    }

    public boolean hasStation(String stationId) {
        return stations.containsKey(stationId);
    }

    public List<MetroStation> getAllStations() {
        return new ArrayList<MetroStation>(stations.values());
    }

    public List<MetroEdge> getOutgoingEdges(String stationId) {
        List<MetroEdge> edges = adjacencyList.get(stationId);
        if (edges == null) {
            return Collections.emptyList();
        }
        return edges;
    }

    /**
     * Case-insensitive prefix/subsequence search across station names.
     */
    public List<MetroStation> searchStations(String query) {
        List<MetroStation> results = new ArrayList<MetroStation>();
        if (query == null || query.trim().isEmpty()) {
            return results;
        }
        String cleanQuery = query.trim().toLowerCase();
        for (MetroStation station : stations.values()) {
            if (station.getName().toLowerCase().contains(cleanQuery) ||
                station.getStationId().toLowerCase().contains(cleanQuery) ||
                station.getLineDisplayName().toLowerCase().contains(cleanQuery)) {
                results.add(station);
            }
        }
        return results;
    }

    public int getVertexCount() {
        return stations.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<MetroEdge> edgeList : adjacencyList.values()) {
            count += edgeList.size();
        }
        return count;
    }
}
