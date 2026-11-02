# Data Dictionary and Dataset Rules

## 1. General rules

- Use stable IDs, not station names, as internal keys.
- Store source URL and verification metadata.
- Use ISO 8601 dates for timestamps.
- Never mix demo records with verified records without an explicit status.
- Unknown values must be null/omitted, not guessed.
- Treat operational status as time-sensitive.

## 2. Enumerations

### operationalStatus
- `OPERATIONAL`
- `PARTIALLY_OPERATIONAL`
- `UNDER_CONSTRUCTION`
- `PLANNED`
- `SUSPENDED`
- `UNKNOWN`

### verificationStatus
- `VERIFIED_OFFICIAL`
- `VERIFIED_SECONDARY`
- `DEMO`
- `UNVERIFIED`

## 3. MetroLine

```json
{
  "lineId": "LINE_1",
  "displayName": "Metro Line 1",
  "officialColour": null,
  "operationalStatus": "UNKNOWN",
  "operator": null,
  "sourceUrl": null,
  "verificationStatus": "UNVERIFIED",
  "lastVerifiedAt": null
}
```

Replace nulls only after checking authoritative sources.

## 4. MetroStation

```json
{
  "stationId": "STATION_EXAMPLE",
  "name": "Example Station",
  "lineIds": ["LINE_1"],
  "latitude": null,
  "longitude": null,
  "operationalStatus": "UNKNOWN",
  "sourceUrl": null,
  "verificationStatus": "UNVERIFIED",
  "lastVerifiedAt": null
}
```

## 5. MetroEdge

```json
{
  "fromStationId": "STATION_A",
  "toStationId": "STATION_B",
  "lineId": "LINE_1",
  "isInterchange": false,
  "travelTimeMinutes": null,
  "sourceUrl": null,
  "verificationStatus": "UNVERIFIED"
}
```

For bidirectional travel, create or interpret the reverse edge consistently. Document the convention.

## 6. FareRecord

```json
{
  "fareId": "FARE_EXAMPLE",
  "lineId": "LINE_1",
  "originStationId": "STATION_A",
  "destinationStationId": "STATION_B",
  "amount": null,
  "currency": "INR",
  "effectiveFrom": null,
  "effectiveTo": null,
  "sourceUrl": null,
  "verificationStatus": "UNVERIFIED",
  "lastVerifiedAt": null
}
```

Do not infer a fare formula or show an official fare unless verified.

## 7. ServiceAlert

```json
{
  "alertId": "ALERT_EXAMPLE",
  "title": "Example notice",
  "description": "DEMO — not a live service alert.",
  "severity": "INFO",
  "affectedLineIds": [],
  "affectedStationIds": [],
  "sourceUrl": null,
  "publishedAt": null,
  "lastVerifiedAt": null,
  "verificationStatus": "DEMO"
}
```

## 8. Source register

Maintain a separate source register with:
- Source organisation.
- Page/document title.
- URL.
- Data fields supported by the source.
- Date checked.
- Person who checked it.
- Notes and limitations.

Prefer official MMRDA and relevant metro operator publications. Do not use a source for facts it does not actually establish.
