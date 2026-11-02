# UI/UX Design Specification

## 1. Brand

**Name:** Metro Mumbai  
**Tagline:** Your City. Your Metro. Your Journey.  
**Personality:** Clear, dependable, modern, commuter-first.  
**Brand rule:** Independent product; do not copy m-Indicator branding.

## 2. Visual direction

- Material 3-inspired Android interface.
- White/off-white surfaces.
- Deep navy primary text.
- Metro line colours used as functional identifiers, not decoration.
- Rounded cards and restrained elevation.
- Clear typography and generous spacing.
- High contrast and touch-friendly controls.
- Avoid dense dashboards and unnecessary gradients.

## 3. Suggested design tokens

These are initial design tokens, not official operator brand colours. Verify corridor colours before use.

| Token | Suggested value | Usage |
|---|---|---|
| Primary | `#16324F` | Main actions, headings |
| Background | `#F7F8FA` | App background |
| Surface | `#FFFFFF` | Cards |
| Text primary | `#17212B` | Main text |
| Text secondary | `#64748B` | Supporting text |
| Border | `#E2E8F0` | Dividers and outlines |
| Success | `#16845B` | Confirmed/available states |
| Warning | `#B7791F` | Data warning |
| Error | `#C2413B` | Error states |

Use official line colours only after verification from official sources.

## 4. Navigation

Persistent bottom navigation:
1. Home
2. Routes
3. Metro Map
4. Saved
5. More

## 5. Screen specifications

### Home
- Header/logo and short greeting.
- From and To station fields.
- Swap button.
- Primary Find Route action.
- Recent journeys.
- Favourite stations.
- Service notice area with timestamp and source.
- Quick links to map and route planner.

### Station Search
- Search input at top.
- Recent and popular stations.
- Suggestions with line badges.
- Interchange indicator.
- Clear no-results state.

### Route Results
- Source/destination summary.
- Recommended route card.
- Alternative routes if valid.
- Stops, transfers and journey metric.
- Fare/time only when verified.
- Open details action.

### Journey Details
- Vertical timeline.
- Boarding, intermediate, transfer and destination states.
- Line labels and interchange instructions.
- Save route action.
- Reverse journey action.

### Metro Map
- Google Map base layer.
- Verified station markers.
- Optional verified route polylines.
- Line filters.
- Station tap details.
- Map/list toggle or schematic map fallback.
- Legend and data disclaimer.

### Station Details
- Station name and line badges.
- Verified interchange lines.
- Previous/next station where known.
- Verified facilities.
- Set as From / Set as To actions.

### Alerts
- Clear alert category.
- Message, source and last-updated timestamp.
- Distinguish live, official notice, manually maintained and demo.
- Empty/unavailable state.

### Saved
- Favourite stations.
- Saved routes.
- Recent history.
- Remove and clear controls.

### More
- Settings and theme.
- Data sources and freshness.
- About and independent-app disclaimer.
- Privacy policy and feedback.

## 6. Interaction rules

- Source/destination fields open station search.
- Swap exchanges source and destination.
- Find Route validates inputs before calculation.
- Route cards open journey details.
- Map markers open station details.
- Save controls provide visible confirmation.
- All buttons must have a real action.
- Use loading, empty, success and error states.

## 7. Accessibility

- Minimum touch target around 48dp where practical.
- Text scaling support.
- Adequate contrast.
- Do not communicate line identity through colour alone; also show line name/number.
- Content descriptions for important icons.
- Clear labels for source and destination.

## 8. Responsive behaviour

Prioritise phone portrait. Support small and large phones, landscape where practical and Android system font scaling. Avoid fixed-width layouts.
