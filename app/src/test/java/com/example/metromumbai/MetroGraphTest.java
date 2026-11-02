package com.example.metromumbai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.metromumbai.corejava.BfsRouteFinder;
import com.example.metromumbai.corejava.DijkstraRouteFinder;
import com.example.metromumbai.corejava.MetroGraph;
import com.example.metromumbai.corejava.MetroStation;
import com.example.metromumbai.corejava.RouteResult;
import com.example.metromumbai.data.MetroDataProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Unit Tests for MCA Advanced Data Structures Graph Algorithms.
 * Tests MetroGraph, BfsRouteFinder, and DijkstraRouteFinder.
 */
public class MetroGraphTest {

    private MetroGraph graph;
    private BfsRouteFinder bfsFinder;
    private DijkstraRouteFinder dijkstraFinder;

    @Before
    public void setUp() {
        graph = MetroDataProvider.INSTANCE.buildMetroGraph();
        bfsFinder = new BfsRouteFinder(graph);
        dijkstraFinder = new DijkstraRouteFinder(graph);
    }

    @Test
    public void testGraphVertexAndEdgeCounts() {
        // Line 1: 12 + Line 7: 14 + Line 2A: 17 + Line 3: 10 = 53 stations
        assertTrue("Graph should have at least 50 stations", graph.getVertexCount() >= 50);
        assertTrue("Graph should have track edges", graph.getEdgeCount() > 50);
    }

    @Test
    public void testDirectLine1Route() {
        // Versova to Ghatkopar (Direct on Line 1)
        RouteResult result = dijkstraFinder.findShortestWeightedRoute(
            "L1_VERSOVA", "L1_GHATKOPAR", DijkstraRouteFinder.OptimizationCriterion.FASTEST_TIME
        );

        assertNotNull("Direct route should be found", result);
        assertEquals(11, result.getTotalStops());
        assertEquals(0, result.getInterchangeCount());
        assertEquals(40, result.getFareInr());
    }

    @Test
    public void testInterchangeRouteVersovaToGundavali() {
        // Versova (Line 1) to Gundavali (Line 7) via WEH Skywalk
        RouteResult result = dijkstraFinder.findShortestWeightedRoute(
            "L1_VERSOVA", "L7_GUNDAVALI", DijkstraRouteFinder.OptimizationCriterion.FASTEST_TIME
        );

        assertNotNull("Interchange route should be found", result);
        assertTrue("Should have 1 interchange via WEH Skywalk", result.getInterchangeCount() >= 1);
        assertEquals("Versova", result.getOrigin().getName());
        assertTrue(result.getDestination().getName().contains("Gundavali"));
        assertTrue(result.getRoundedTimeMinutes() > 0);
    }

    @Test
    public void testBfsFewestStopsRoute() {
        RouteResult bfsResult = bfsFinder.findShortestStopRoute("L1_VERSOVA", "L7_GUNDAVALI");
        assertNotNull("BFS route must be found", bfsResult);
        assertTrue("Total stops should be positive", bfsResult.getTotalStops() > 0);
    }

    @Test
    public void testSameSourceAndDestination() {
        RouteResult result = dijkstraFinder.findShortestWeightedRoute(
            "L1_VERSOVA", "L1_VERSOVA", DijkstraRouteFinder.OptimizationCriterion.FASTEST_TIME
        );

        assertNotNull(result);
        assertEquals(0, result.getTotalStops());
        assertEquals(0.0, result.getTotalTimeMinutes(), 0.001);
    }

    @Test
    public void testUnknownStationHandling() {
        RouteResult result = dijkstraFinder.findShortestWeightedRoute(
            "UNKNOWN_STATION_A", "L1_VERSOVA", DijkstraRouteFinder.OptimizationCriterion.FASTEST_TIME
        );
        assertNull("Non-existent station should return null gracefully", result);
    }

    @Test
    public void testCaseInsensitiveStationSearch() {
        List<MetroStation> searchResults = graph.searchStations("andheri");
        assertTrue("Search for 'andheri' should return matching stations", searchResults.size() >= 1);
    }
}
