# Test Plan

## 1. Unit tests — graph model

- Add station and edge.
- Reject duplicate station IDs or handle them deterministically.
- Unknown station lookup.
- Disconnected graph.
- Multiple lines at an interchange.
- Reverse-edge convention.
- Negative weight rejection for Dijkstra.

## 2. BFS tests

- Direct connection.
- Multi-stop route.
- Two paths with different stop counts.
- Source equals destination.
- No route exists.
- Verify returned route has minimum edge count.

## 3. Dijkstra tests

- Shortest weighted path.
- Alternative path with lower total weight.
- Zero-weight edge if allowed by model.
- Negative edge rejected.
- Unreachable destination.
- Verify predecessor reconstruction.
- Verify selected metric is clearly identified.

## 4. Interchange tests

- Route remains on one line.
- Route changes line once.
- Route changes line multiple times.
- Same station name represented by line-specific nodes.
- Transfer edge is missing or unverified.
- Transfer count matches actual line changes.

## 5. UI tests

- Empty station search.
- Search with different letter case.
- No station found.
- Same source and destination.
- Swap stations.
- Route card opens details.
- Save/remove favourite.
- Clear history.
- Map marker opens correct station.
- Back navigation works.

## 6. Offline tests

- Enable airplane mode.
- Search local station dataset.
- Calculate route.
- Open saved routes.
- Map tiles may be unavailable; app must still offer list-based route information.

## 7. Data integrity tests

- Every edge endpoint references an existing station node.
- Every line ID referenced by a station exists.
- Coordinates are within valid latitude/longitude ranges.
- No unverified fare appears as official.
- No demo alert is labelled live.
- Planned/under-construction corridors are excluded from active graph.

## 8. Release checks

- Gradle sync succeeds.
- Debug APK builds.
- App installs and launches.
- No crash on empty or corrupt dataset.
- API key is not committed.
- Release signing works where required.
- Screenshots and test evidence are captured.
