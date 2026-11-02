# Project Task Plan

## Phase 0 — Approval and setup
- [ ] Confirm project title with faculty.
- [ ] Confirm team size and member names.
- [ ] Confirm whether Kotlin UI with Java algorithm module is accepted, or Java-only is required.
- [ ] Create Git repository.
- [ ] Create Android Studio project from AI Studio export.
- [ ] Confirm build succeeds before adding features.

## Phase 1 — Requirements and design
- [ ] Review PRD, TRD and architecture.
- [ ] Create Stitch screens and design system.
- [ ] Create navigation prototype.
- [ ] Review station/corridor scope.
- [ ] Create official source register.

## Phase 2 — Verified dataset
- [ ] Verify initial corridors and operational status.
- [ ] Verify station names and order.
- [ ] Verify interchange relationships.
- [ ] Verify coordinates for map markers.
- [ ] Record source URLs and verification dates.
- [ ] Mark unknown fare/time/facility data as unavailable.
- [ ] Validate JSON for duplicate IDs and invalid references.

## Phase 3 — Java route engine
- [ ] Implement MetroStation.
- [ ] Implement MetroEdge.
- [ ] Implement MetroGraph adjacency list.
- [ ] Implement BFS minimum-stop route.
- [ ] Implement Dijkstra weighted route.
- [ ] Implement predecessor-based reconstruction.
- [ ] Implement line-change/interchange detection.
- [ ] Add disconnected-network and invalid-input handling.
- [ ] Add JUnit tests and complexity notes.

## Phase 4 — Android application
- [ ] Implement home screen.
- [ ] Implement station search.
- [ ] Connect Java route engine to UI.
- [ ] Implement route results.
- [ ] Implement journey details.
- [ ] Implement saved routes and history.
- [ ] Implement station details.
- [ ] Implement settings and about.

## Phase 5 — Google Maps
- [ ] Configure Maps SDK and API key.
- [ ] Restrict API key to Android package and signing certificate.
- [ ] Add verified station markers.
- [ ] Add line/route overlays only when geometry is valid.
- [ ] Add marker-to-station details navigation.
- [ ] Add map failure/offline fallback.
- [ ] Verify map usage, billing and terms.

## Phase 6 — Alerts and optional services
- [ ] Decide whether a reliable official alert source exists.
- [ ] If not, show unavailable state.
- [ ] If manually maintained, show source and timestamp.
- [ ] Never label sample alerts as live.

## Phase 7 — Testing
- [ ] Run unit tests for BFS.
- [ ] Run unit tests for Dijkstra.
- [ ] Test interchange route.
- [ ] Test no-route case.
- [ ] Test same source/destination.
- [ ] Test offline route planning.
- [ ] Test map marker accuracy.
- [ ] Test small and large Android screens.
- [ ] Test app after process restart.

## Phase 8 — Release and submission
- [ ] Fix all build errors.
- [ ] Generate debug APK.
- [ ] Generate signed release APK if required.
- [ ] Prepare README and setup instructions.
- [ ] Prepare screenshots and demo script.
- [ ] Prepare algorithm explanation and complexity.
- [ ] Prepare source/attribution list.
- [ ] Confirm faculty submission format.

## Definition of done

A task is done only when implementation exists, relevant tests pass, UI is connected, data is traceable and the result can be demonstrated. A generated mockup or AI response alone does not count as a completed feature.
