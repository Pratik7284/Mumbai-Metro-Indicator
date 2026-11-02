package com.example.metromumbai.corejava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Dijkstra's Shortest Path Algorithm for Mumbai Metro.
 * Computes optimal routes minimizing weighted travel time, interchange penalties,
 * and distance metrics.
 *
 * Algorithm: Dijkstra using Min-Priority Queue (Binary Heap)
 * Time Complexity: O((V + E) log V)
 * Space Complexity: O(V) for heap, distance map, and predecessor map.
 */
public class DijkstraRouteFinder {
    public enum OptimizationCriterion {
        FASTEST_TIME,
        MINIMUM_INTERCHANGES,
        LOWEST_FARE
    }

    private static class NodeEntry implements Comparable<NodeEntry> {
        final String stationId;
        final double tentativeWeight;

        NodeEntry(String stationId, double tentativeWeight) {
            this.stationId = stationId;
            this.tentativeWeight = tentativeWeight;
        }

        @Override
        public int compareTo(NodeEntry other) {
            return Double.compare(this.tentativeWeight, other.tentativeWeight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeEntry nodeEntry = (NodeEntry) o;
            return Objects.equals(stationId, nodeEntry.stationId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stationId);
        }
    }

    private final MetroGraph graph;

    public DijkstraRouteFinder(MetroGraph graph) {
        this.graph = graph;
    }

    /**
     * Computes the lowest-cost route based on the selected optimization criterion.
     */
    public RouteResult findShortestWeightedRoute(String sourceId, String destinationId,
                                                OptimizationCriterion criterion) {
        if (sourceId == null || destinationId == null) return null;
        if (!graph.hasStation(sourceId) || !graph.hasStation(destinationId)) return null;

        MetroStation source = graph.getStation(sourceId);
        MetroStation destination = graph.getStation(destinationId);

        if (sourceId.equals(destinationId)) {
            List<MetroStation> singleList = Collections.singletonList(source);
            List<RouteStep> singleStep = Collections.singletonList(
                new RouteStep(source, RouteStep.StepType.BOARD, source.getLineId(),
                    "Already at destination: " + source.getName(), 0.0, "Platform 1")
            );
            return new RouteResult("dijkstra_same", source, destination, singleList, singleStep,
                Collections.singletonList(source.getLineId()), Collections.<MetroStation>emptyList(),
                0, 0, 0.0, 0.0, 0, "Dijkstra (Fastest Time)", true, "Same Station");
        }

        PriorityQueue<NodeEntry> minHeap = new PriorityQueue<NodeEntry>();
        Map<String, Double> distances = new HashMap<String, Double>();
        Map<String, MetroEdge> parentEdge = new HashMap<String, MetroEdge>();
        Set<String> settled = new HashSet<String>();

        for (MetroStation st : graph.getAllStations()) {
            distances.put(st.getStationId(), Double.POSITIVE_INFINITY);
        }

        distances.put(sourceId, 0.0);
        minHeap.add(new NodeEntry(sourceId, 0.0));

        boolean found = false;

        while (!minHeap.isEmpty()) {
            NodeEntry currentEntry = minHeap.poll();
            String currentId = currentEntry.stationId;

            if (settled.contains(currentId)) continue;
            settled.add(currentId);

            if (currentId.equals(destinationId)) {
                found = true;
                break;
            }

            double currentDist = currentEntry.tentativeWeight;

            for (MetroEdge edge : graph.getOutgoingEdges(currentId)) {
                String neighborId = edge.getToStationId();
                if (settled.contains(neighborId)) continue;

                // Calculate edge weight according to chosen criterion
                double edgeWeight = getEdgeWeight(edge, criterion);
                double newDist = currentDist + edgeWeight;

                if (newDist < distances.get(neighborId)) {
                    distances.put(neighborId, newDist);
                    parentEdge.put(neighborId, edge);
                    minHeap.add(new NodeEntry(neighborId, newDist));
                }
            }
        }

        if (!found) {
            return null; // Destination unreachable
        }

        // Reconstruct path via predecessor map
        List<MetroEdge> pathEdges = new ArrayList<MetroEdge>();
        String curr = destinationId;
        while (!curr.equals(sourceId)) {
            MetroEdge edge = parentEdge.get(curr);
            if (edge == null) break;
            pathEdges.add(edge);
            curr = edge.getFromStationId();
        }
        Collections.reverse(pathEdges);

        String title = "Recommended Route";
        if (criterion == OptimizationCriterion.FASTEST_TIME) {
            title = "Fastest Route";
        } else if (criterion == OptimizationCriterion.MINIMUM_INTERCHANGES) {
            title = "Fewest Interchanges Route";
        } else if (criterion == OptimizationCriterion.LOWEST_FARE) {
            title = "Lowest Fare Route";
        }

        return buildRouteResult("dijkstra_" + criterion.name().toLowerCase() + "_" + sourceId + "_" + destinationId,
            source, destination, pathEdges, "Dijkstra (" + title + ")", true, title);
    }

    private double getEdgeWeight(MetroEdge edge, OptimizationCriterion criterion) {
        if (criterion == OptimizationCriterion.FASTEST_TIME) {
            // Travel time + transfer penalty
            double weight = edge.getTravelTimeMinutes();
            if (edge.isInterchange()) {
                weight += 2.0; // 2 min walking penalty to favor direct lines
            }
            return weight;
        } else if (criterion == OptimizationCriterion.MINIMUM_INTERCHANGES) {
            // Heavily penalize interchanges so algorithm avoids changing lines
            double weight = 1.0;
            if (edge.isInterchange()) {
                weight += 100.0;
            }
            return weight;
        } else {
            // Lowest fare: proportional to distance
            return edge.getDistanceKm();
        }
    }

    private RouteResult buildRouteResult(String id, MetroStation source, MetroStation destination,
                                         List<MetroEdge> edges, String algorithm, boolean recommended,
                                         String title) {
        List<MetroStation> stationSeq = new ArrayList<MetroStation>();
        List<RouteStep> steps = new ArrayList<RouteStep>();
        List<String> lineSeq = new ArrayList<String>();
        List<MetroStation> interchanges = new ArrayList<MetroStation>();

        stationSeq.add(source);
        lineSeq.add(source.getLineId());

        double cumulativeTime = 0.0;
        double cumulativeDistance = 0.0;
        int interchangeCount = 0;

        String currentLine = source.getLineId();

        steps.add(new RouteStep(source, RouteStep.StepType.BOARD, currentLine,
            "Board " + source.getLineDisplayName() + " towards " + destination.getName(),
            0.0, "Platform 2 • Air Conditioned Rake"));

        for (int i = 0; i < edges.size(); i++) {
            MetroEdge edge = edges.get(i);
            MetroStation toStation = graph.getStation(edge.getToStationId());
            stationSeq.add(toStation);

            cumulativeTime += edge.getTravelTimeMinutes();
            cumulativeDistance += edge.getDistanceKm();

            boolean isLast = (i == edges.size() - 1);

            if (edge.isInterchange() || !edge.getLineId().equals(currentLine)) {
                interchangeCount++;
                interchanges.add(toStation);
                if (!lineSeq.contains(edge.getLineId())) {
                    lineSeq.add(edge.getLineId());
                }
                steps.add(new RouteStep(toStation, RouteStep.StepType.INTERCHANGE_TRANSFER,
                    edge.getLineId(),
                    "Transfer via " + edge.getDescription() + " to " + toStation.getLineDisplayName(),
                    cumulativeTime, "Direct Skywalk / Concourse"));
                currentLine = edge.getLineId();
            } else if (isLast) {
                steps.add(new RouteStep(toStation, RouteStep.StepType.ALIGHT, currentLine,
                    "Arrive at destination: " + toStation.getName() + ". " + toStation.getExitNote(),
                    cumulativeTime, "Exit Gate 3"));
            } else {
                steps.add(new RouteStep(toStation, RouteStep.StepType.INTERMEDIATE, currentLine,
                    "Pass " + toStation.getName(),
                    cumulativeTime, "Platform Concourse"));
            }
        }

        int totalStops = edges.size();
        int fareInr = calculateFare(cumulativeDistance);

        return new RouteResult(id, source, destination, stationSeq, steps, lineSeq,
            interchanges, totalStops, interchangeCount, cumulativeTime, cumulativeDistance,
            fareInr, algorithm, recommended, title);
    }

    private int calculateFare(double distanceKm) {
        if (distanceKm <= 3.0) return 10;
        if (distanceKm <= 6.0) return 20;
        if (distanceKm <= 9.0) return 30;
        if (distanceKm <= 12.0) return 40;
        if (distanceKm <= 18.0) return 50;
        return 60;
    }
}
