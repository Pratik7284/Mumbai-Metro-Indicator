# Architecture and Product Decisions

## ADR-001 — Offline-first MVP
**Decision:** Core station search and route planning use a local dataset.  
**Reason:** Reliable route planning should not depend on network availability or a custom backend.  
**Consequence:** Dataset updates require a controlled update/import process.

## ADR-002 — Java algorithm core
**Decision:** Implement graph model, BFS and Dijkstra in pure Java.  
**Reason:** The academic subject is Advanced Data Structures using Java.  
**Consequence:** If the AI-generated UI is Kotlin, call Java classes from Kotlin and explain the mixed-language architecture honestly.

## ADR-003 — AI Studio Android output
**Decision:** Inspect the generated language and framework before assuming Java.  
**Reason:** Native Android generation may use Kotlin and Jetpack Compose.  
**Consequence:** Faculty approval is needed if the submission rubric demands Java-only.

## ADR-004 — Google Maps is a visual map provider
**Decision:** Use Google Maps SDK to render map tiles and station markers, not as the source of metro network truth.  
**Reason:** A map SDK does not automatically provide verified Mumbai Metro fares, station graph, transfer rules or live service notices.  
**Consequence:** Maintain a separate verified metro dataset and route engine.

## ADR-005 — No fabricated live data
**Decision:** Do not display live status, fares or travel times unless a reliable source is connected and timestamped.  
**Reason:** Incorrect transit information can mislead passengers.  
**Consequence:** Show unavailable states when data is missing.

## ADR-006 — Limited initial corridor scope
**Decision:** Start with a small set of verified operational corridors, then expand.  
**Reason:** Accurate data is more important than an inflated station count.  
**Consequence:** Some journeys may not be routable in the first version.

## ADR-007 — No custom backend in MVP
**Decision:** Use local persistence initially.  
**Reason:** Avoid unnecessary hosting, authentication and operational complexity.  
**Consequence:** Multi-device sync and centrally managed alerts are deferred.
