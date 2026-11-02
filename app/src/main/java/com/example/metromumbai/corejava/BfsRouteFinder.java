package com.example.metromumbai.corejava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth-First Search (BFS) Route Planner for Mumbai Metro.
 * Finds the route between source and destination stations that minimizes
 * the total number of stops/stations traversed.
 *
 * Algorithm: BFS traversal on unweighted graph
 * Time Complexity: O(V + E)
 * Space Complexity: O(V) for queue, visited set, and predecessor map.
 */
public class BfsRouteFinder {
    private final MetroGraph graph;

    public BfsRouteFinder(MetroGraph graph) {
        this.graph = graph;
    }

    /**
     * Executes BFS to find the minimum-stop path.
     *
     * @param sourceId Station ID of starting station
     * @param destinationId Station ID of destination station
     * @return RouteResult containing reconstructed path and analytics, or null if no path exists.
     */
    public RouteResult findShortestStopRoute(String sourceId, String destinationId) {
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
            return new RouteResult("bfs_same", source, destination, singleList, singleStep,
                Collections.singletonList(source.getLineId()), Collections.<MetroStation>emptyList(),
                0, 0, 0.0, 0.0, 0, "BFS (Minimum Stops)", true, "Same Station");
        }

        Queue<String> queue = new LinkedList<String>();
        Set<String> visited = new HashSet<String>();
        Map<String, MetroEdge> parentEdge = new HashMap<String, MetroEdge>();

        queue.add(sourceId);
        visited.add(sourceId);

        boolean reached = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destinationId)) {
                reached = true;
                break;
            }

            for (MetroEdge edge : graph.getOutgoingEdges(current)) {
                String neighbor = edge.getToStationId();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentEdge.put(neighbor, edge);
                    queue.add(neighbor);
                }
            }
        }

        if (!reached) {
            return null; // Disconnected graph or unreached destination
        }

        // Reconstruct path from destination backwards to source
        List<MetroEdge> pathEdges = new ArrayList<MetroEdge>();
        String curr = destinationId;
        while (!curr.equals(sourceId)) {
            MetroEdge edge = parentEdge.get(curr);
            if (edge == null) break;
            pathEdges.add(edge);
            curr = edge.getFromStationId();
        }
        Collections.reverse(pathEdges);

        return buildRouteResult("bfs_" + sourceId + "_" + destinationId,
            source, destination, pathEdges, "BFS (Minimum Stops)", false, "Fewest Stops Route");
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
            0.0, "Platform 1/2"));

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
                    "Transfer to " + toStation.getLineDisplayName() + ". " + edge.getDescription(),
                    cumulativeTime, "Follow Concourse Wayfinding"));
                currentLine = edge.getLineId();
            } else if (isLast) {
                steps.add(new RouteStep(toStation, RouteStep.StepType.ALIGHT, currentLine,
                    "Alight at " + toStation.getName() + " Terminal. " + toStation.getExitNote(),
                    cumulativeTime, "Concourse Exit Gates"));
            } else {
                steps.add(new RouteStep(toStation, RouteStep.StepType.INTERMEDIATE, currentLine,
                    "Pass through " + toStation.getName(),
                    cumulativeTime, "Platform Concourse"));
            }
        }

        int totalStops = edges.size();
        int fareInr = calculateFare(cumulativeDistance, interchangeCount);

        return new RouteResult(id, source, destination, stationSeq, steps, lineSeq,
            interchanges, totalStops, interchangeCount, cumulativeTime, cumulativeDistance,
            fareInr, algorithm, recommended, title);
    }

    /**
     * Standard Mumbai Metro Fare Slabs based on distance in kilometers.
     */
    private int calculateFare(double distanceKm, int interchanges) {
        if (distanceKm <= 3.0) return 10;
        if (distanceKm <= 6.0) return 20;
        if (distanceKm <= 9.0) return 30;
        if (distanceKm <= 12.0) return 40;
        if (distanceKm <= 18.0) return 50;
        return 60;
    }
}
