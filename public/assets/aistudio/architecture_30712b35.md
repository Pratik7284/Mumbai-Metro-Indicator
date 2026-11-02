# System Architecture

## 1. Overview

Use an offline-first, layered Android architecture. The route engine must be independent from UI, Google Maps rendering and data persistence.

```text
Android App
├── Presentation
│   ├── Home
│   ├── Station Search
│   ├── Route Results
│   ├── Journey Details
│   ├── Metro Map
│   ├── Station Details
│   ├── Alerts
│   └── Saved / Settings
├── Domain
│   ├── Search Stations
│   ├── Find Minimum Stops (BFS)
│   ├── Find Minimum Weight (Dijkstra)
│   ├── Reconstruct Route
│   └── Identify Interchanges
├── Data
│   ├── Station Repository
│   ├── Line Repository
│   ├── Route Repository
│   ├── Local JSON Import
│   └── Room / SQLite
├── Maps Adapter
│   ├── Google Maps SDK
│   ├── Station Markers
│   └── Route Polylines
└── Java Core
    ├── MetroGraph
    ├── MetroStation
    ├── MetroEdge
    ├── BfsRouteFinder
    ├── DijkstraRouteFinder
    └── RouteResult
```

## 2. Architectural principles

- Single source of truth for station and line data.
- Route calculations do not depend on Google Maps.
- Map rendering does not determine route validity.
- UI observes view-model state and does not contain graph algorithms.
- Repositories isolate storage and data-source details.
- Verified facts and demo data are never mixed without labels.
- Local routing remains usable offline.

## 3. Core domain model

### MetroStation
- stationId: String
- name: String
- lineIds: List<String>
- latitude: Double?
- longitude: Double?
- operationalStatus: enum
- sourceUrl: String?
- verificationStatus: enum
- lastVerifiedAt: String?

### MetroLine
- lineId: String
- displayName: String
- officialColour: String?
- operationalStatus: enum
- operator: String?
- sourceUrl: String?
- lastVerifiedAt: String?

### MetroEdge
- fromStationId: String
- toStationId: String
- lineId: String
- isInterchange: Boolean
- travelTimeMinutes: Double?
- sourceUrl: String?
- verificationStatus: enum

### RouteResult
- stationIds: List<String>
- lineIds: List<String>
- interchangeStationIds: List<String>
- totalStops: Int
- interchangeCount: Int
- totalWeight: Double?
- weightType: enum (STOPS, VERIFIED_TIME, DEMO_WEIGHT)
- dataWarnings: List<String>

## 4. Graph modelling and interchange handling

A naive graph with one vertex per station may lose information when multiple lines share an interchange. Prefer line-specific station nodes or an explicit transfer edge.

Example:
- `DN_NAGAR_LINE1`
- `DN_NAGAR_LINE2A`

Connect these nodes with an interchange/transfer edge only when the interchange is verified. This makes line changes explicit and lets the route engine count transfers correctly.

The UI may display both nodes as one named station with multiple line badges.

## 5. Algorithm design

### BFS — minimum stops
1. Validate source and destination.
2. Initialise queue with source.
3. Track visited nodes.
4. Store parent for each discovered node.
5. Stop when destination is reached.
6. Reconstruct path from parent map.
7. Convert line-specific nodes into passenger-facing station/line steps.

### Dijkstra — minimum weighted journey
1. Reject negative or missing weights for the selected metric.
2. Initialise distance map to infinity.
3. Set source distance to zero.
4. Push source into min-priority queue.
5. Pop the node with minimum tentative distance.
6. Relax outgoing edges.
7. Store predecessor on improvement.
8. Reconstruct path when destination is settled.

If verified time weights are unavailable, do not label a generic weight as “fastest journey”. Either omit the fastest option or clearly label a demo/educational estimate.

## 6. Data flow

### Route search
User input → ViewModel → RouteUseCase → MetroGraph → BFS/Dijkstra → RouteResult → ViewModel state → UI.

### Map station selection
Map tap → station ID → repository lookup → station details screen.

### Dataset refresh
Versioned JSON → validation → repository/database update → data version and verification metadata.

## 7. Error handling

- Unknown station ID: validation error.
- Same source/destination: user-facing validation.
- No path: “No route found in the currently available network.”
- Missing fare: “Fare data not verified.”
- Missing time weights: do not show a fastest-time claim.
- No map network: retain list and route functionality; show map unavailable state.
- Corrupt dataset: reject import and retain last known-good local dataset.

## 8. Suggested package layout

```text
app/
  src/main/
    java/com/example/metromumbai/
      presentation/
      data/
      domain/
      maps/
      corejava/
      navigation/
    assets/metro/
      lines.json
      stations.json
      edges.json
      fares.json
```

The exact package path can be changed to match the generated project.
