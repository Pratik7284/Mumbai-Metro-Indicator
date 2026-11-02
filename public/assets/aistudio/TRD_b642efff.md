# Technical Requirements Document (TRD)

## 1. Platform and implementation strategy

### Recommended AI Studio-compatible implementation
- Native Android project generated through Google AI Studio Android workflow.
- Kotlin + Jetpack Compose for presentation, if that is what the selected builder generates.
- Pure Java core module/package for the MCA route algorithms.
- Android Studio + Gradle for final project validation and APK generation.

### Java-only alternative
If the faculty requires all application code to be Java:
- Java + XML layouts.
- AndroidX Navigation or Activity/Fragment navigation.
- Google Maps SDK for Android Java APIs.
- Java route-engine classes.
- SQLite/Room Java-compatible persistence.

Do not describe a Kotlin/Compose app as Java-only. Confirm the requirement with the faculty.

## 2. Core technology

| Layer | Technology |
|---|---|
| Android UI | Kotlin + Jetpack Compose, or Java + XML if required |
| Architecture | MVVM / repository pattern |
| Route engine | Pure Java |
| Local storage | Room/SQLite |
| Maps | Google Maps SDK for Android |
| Dataset | Versioned JSON imported into local database |
| Build | Gradle, Android Studio |
| Testing | JUnit; Android UI tests where practical |

No remote backend is required for the first offline-first MVP. Add a backend only if a verified alert feed, managed data updates or multi-device sync is genuinely needed.

## 3. Functional technical requirements

- Search station names with case-insensitive matching.
- Store stations and lines with stable IDs.
- Store adjacency as a graph.
- Support BFS and Dijkstra route queries.
- Use a priority queue for Dijkstra.
- Reconstruct paths using a predecessor map.
- Identify line changes and interchange nodes.
- Keep fare and travel-time data separate from station connectivity.
- Display data source and last-verified date.
- Persist favourites and recent searches locally.

## 4. Advanced data structures

### Graph
Use an adjacency-list representation:
`Map<String, List<Edge>> adjacencyList`

A station is a vertex. A valid direct metro connection is an edge. Where necessary, model line-specific station nodes separately to represent transfers accurately.

### BFS
Use a queue and visited set to find a route with minimum number of edges/stops in an unweighted graph. Complexity: O(V + E).

### Dijkstra
Use a min-priority queue to find the minimum total non-negative edge weight. Use verified journey-time weights or a clearly defined educational/demo weight. Complexity with adjacency list and binary heap: O((V + E) log V).

### HashMap
Use station ID/name lookup and predecessor maps.

### HashSet
Use visited/settled node tracking.

### ArrayList
Use ordered route results and station collections.

### Parent map
Reconstruct the path from destination back to source, then reverse it.

Do not claim that BFS gives the fastest journey. BFS minimises edge count in an unweighted graph. Dijkstra minimises the selected non-negative weight.

## 5. Maps requirements

- Use Google Maps SDK for Android to render the base map.
- Add markers only for stations with verified latitude/longitude.
- Use polylines only when coordinate geometry is sourced or responsibly derived.
- A Google Map is not a metro route database.
- Google Maps SDK does not automatically provide Mumbai Metro fare tables, platform data, train frequency or official service alerts.
- Keep the graph route engine independent of the map UI.
- Provide a schematic map fallback if map tiles are unavailable.

## 6. Data requirements

Every station record should include:
- `stationId`
- `name`
- `lineIds`
- `latitude` and `longitude` if verified
- `operationalStatus`
- `sourceUrl`
- `verificationStatus`
- `lastVerifiedAt`

Every edge should include:
- `fromStationId`
- `toStationId`
- `lineId`
- `isInterchange`
- `travelTimeMinutes` only if verified
- `sourceUrl`
- `verificationStatus`

Fare records must be independent and include operator/line, origin/destination or fare band, amount, currency, validity/effective date and source.

## 7. Performance and reliability

- Search should feel responsive for the full local dataset.
- Run route calculations off the main/UI thread for larger datasets.
- Handle disconnected graph components.
- Prevent negative Dijkstra weights.
- Validate IDs and duplicate station records during dataset import.
- Handle offline map tiles/network loss gracefully; offline route search must remain available.

## 8. API and backend

MVP: no custom backend.

Optional later backend:
- REST API for versioned station data and verified service notices.
- HTTPS only.
- Schema validation and data versioning.
- Cache responses locally.
- Do not build a proxy for Google Maps APIs unless explicitly supported by their terms.
- Never place privileged server credentials in the Android app.

## 9. Build requirements

- Project opens and syncs in Android Studio.
- Debug APK builds through Gradle.
- Release APK is signed with a user-controlled keystore.
- API keys are configured outside source control.
- README explains setup and build steps.
