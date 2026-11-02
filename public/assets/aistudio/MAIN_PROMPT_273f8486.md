# MASTER BUILD PROMPT — GOOGLE AI STUDIO

You are a senior Android engineer, product designer, data engineer and QA engineer. Build a complete Android application named **Metro Mumbai — Smart Metro Guide** using the attached project documentation as the source of truth.

## Mandatory project context

Read and follow these files:
- `PRD.md`
- `TRD.md`
- `architecture.md`
- `design.md`
- `role.md`
- `member.md`
- `task.md`
- `data-dictionary.md`
- `testing.md`
- `security-privacy.md`
- `DECISIONS.md`
- `deployment.md`

If a requirement conflicts, follow this priority:
1. Data accuracy and safety.
2. PRD scope.
3. Architecture and TRD.
4. Design specification.
5. Task plan.

Ask before silently changing a major architecture decision.

## Product

Build an original Mumbai Metro passenger application inspired by commuter-app convenience. It is ONLY for Mumbai Metro. Do not include Mumbai local trains, BEST buses, taxis, autos or other cities. Do not copy m-Indicator branding, logo or proprietary UI.

## Android and academic technology

The selected Android workflow may generate Kotlin + Jetpack Compose. Inspect the generated framework and state it accurately.

The MCA Advanced Data Structures subject uses Java. Implement the core route algorithms and graph model as pure Java classes, callable from the Android presentation layer. If the user or faculty requires Java-only, use Java + XML instead of Kotlin Compose; do not claim Kotlin is Java.

Required Java structures/algorithms:
- Graph using adjacency lists.
- BFS for minimum stops in an unweighted graph.
- Dijkstra for minimum non-negative weighted cost/time when valid weights exist.
- PriorityQueue for Dijkstra.
- HashMap for station lookup, distances and parent map.
- HashSet for visited/settled nodes.
- ArrayList for ordered results.
- Predecessor-based path reconstruction.
- Explicit interchange/transfer modelling.

Document time complexity and add unit tests. Do not merely mention algorithms in comments; execute them in the real route planner.

## Data scope and truthfulness

Start with Mumbai Metro corridors that have been independently verified from official MMRDA/operator sources. The initial candidate scope is Line 1, Line 2A and Line 7, but verify current operational status, station sequence, interchanges and station names before adding them to the active graph.

Do not assume planned or under-construction corridors are operational. Do not invent station names, coordinates, interchanges, facilities, fares, journey times, frequencies or live alerts.

Every data record should carry source URL, verification status and last verified date. If exact information is unavailable, display “Not verified” or “Unavailable”.

## Google Maps

Use Google Maps SDK for Android to render the geographic map, station markers and supported overlays. Keep route computation independent of Google Maps.

Only add markers for stations with verified coordinates. Do not invent route geometry. Provide a list/schematic fallback if map tiles fail or the device is offline.

Google Maps SDK does not automatically supply the Mumbai Metro station graph, official fares or live operational notices. Do not pretend that selecting Google Maps connects those data sources.

Do not hardcode an unrestricted API key. Explain required Maps SDK enablement, billing and Android application restrictions. Keep keys out of public source control.

## Required app screens

1. Splash.
2. Home dashboard.
3. Station search/autocomplete.
4. Route results with recommended and valid alternative paths.
5. Journey details with boarding, stops, transfers and destination.
6. Metro map with verified markers and line overlays.
7. Station details.
8. Service alerts with source and timestamp.
9. Saved routes/favourite stations/recent searches.
10. More/settings/about/privacy.

Use bottom navigation: Home, Routes, Metro Map, Saved, More.

## UX requirements

- Premium, modern, mobile-first commuter interface.
- Material 3-inspired visual language.
- White/off-white surfaces, deep navy text, restrained accents.
- Use verified metro line colours.
- Readable typography and touch-friendly controls.
- Every button must perform a real action.
- Include loading, empty, error and unavailable states.
- No placeholder screens or dead buttons.

## Offline and storage

Core station search, route planning and saved routes must work offline. Use a versioned local dataset and Room/SQLite or an appropriate local persistence solution. No login is required for MVP. Do not require a Gemini API key for normal route planning.

## Build sequence

Work in small, verifiable milestones. Do not attempt a superficial one-shot mockup.

Milestone 1: Inspect the generated Android project and report language/framework, package name and build configuration. Create navigation skeleton and theme.

Milestone 2: Define and validate the station/line/edge dataset. Include source metadata. Exclude unverified corridors from active route graph.

Milestone 3: Implement pure Java graph classes, BFS and Dijkstra. Add unit tests and explain complexity.

Milestone 4: Connect station search and route engine to Android UI. Implement route results and journey details.

Milestone 5: Integrate Google Maps SDK with verified station coordinates and station-marker navigation. Add map fallback.

Milestone 6: Implement favourites, recent searches, alerts/unavailable state, settings and about.

Milestone 7: Run tests, fix compilation/runtime errors, audit all controls, and provide Android Studio APK build instructions.

## Required deliverables

- Complete Android Studio project.
- Working route planner.
- Java source code for graph algorithms.
- Verified or explicitly labelled dataset.
- Google Maps integration with setup notes.
- Unit tests.
- README and documentation.
- Buildable APK instructions.
- Clear list of implemented features, limitations and remaining work.

## Completion rule

Do not say the app is complete merely because a UI was generated. A feature is complete only after it is implemented, connected, tested and demonstrated. Do not fabricate test results, data sources, API availability or APK build success.

Begin by reading the project documents and reporting the implementation plan and any missing credentials/data. Then implement Milestone 1.
