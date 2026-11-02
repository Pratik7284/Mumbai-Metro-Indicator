package com.example.metromumbai.data

import com.example.metromumbai.corejava.BfsRouteFinder
import com.example.metromumbai.corejava.DijkstraRouteFinder
import com.example.metromumbai.corejava.MetroGraph
import com.example.metromumbai.corejava.MetroStation
import com.example.metromumbai.corejava.RouteResult

class MetroRepository {
    val graph: MetroGraph = MetroDataProvider.buildMetroGraph()
    private val dijkstraFinder = DijkstraRouteFinder(graph)
    private val bfsFinder = BfsRouteFinder(graph)

    fun getStation(id: String): MetroStation? = graph.getStation(id)

    fun getAllStations(): List<MetroStation> = graph.allStations

    fun searchStations(query: String): List<MetroStation> = graph.searchStations(query)

    fun calculateFastestRoute(sourceId: String, destId: String): RouteResult? {
        return dijkstraFinder.findShortestWeightedRoute(
            sourceId, destId, DijkstraRouteFinder.OptimizationCriterion.FASTEST_TIME
        )
    }

    fun calculateMinInterchangesRoute(sourceId: String, destId: String): RouteResult? {
        return dijkstraFinder.findShortestWeightedRoute(
            sourceId, destId, DijkstraRouteFinder.OptimizationCriterion.MINIMUM_INTERCHANGES
        )
    }

    fun calculateLowestFareRoute(sourceId: String, destId: String): RouteResult? {
        return dijkstraFinder.findShortestWeightedRoute(
            sourceId, destId, DijkstraRouteFinder.OptimizationCriterion.LOWEST_FARE
        )
    }

    fun calculateBfsRoute(sourceId: String, destId: String): RouteResult? {
        return bfsFinder.findShortestStopRoute(sourceId, destId)
    }
}
