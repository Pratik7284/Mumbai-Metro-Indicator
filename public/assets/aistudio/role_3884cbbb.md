# Roles and Permissions

## 1. MVP roles

The first release is designed for public passengers and does not require authentication.

| Role | Description | Permissions |
|---|---|---|
| Guest / Passenger | Normal app user | Search stations, plan routes, view map and station details, view alerts, save favourites locally |
| Local Device Owner | Person using the device | Manage local favourites, recent searches, theme and privacy settings |
| Project Maintainer | Developer/team maintaining dataset | Prepare and validate station/line/fare/alert data before app release |
| Administrator (future only) | Optional backend operator | Publish approved dataset updates and service notices through a secured admin system |

## 2. Permission principles

- No account is required for core route planning.
- Location permission is optional and must be requested only if a “near me” feature is implemented.
- The user can search stations manually without location permission.
- Do not collect contacts, SMS, call logs or unrelated device data.
- Do not expose an admin interface in the public passenger app.
- Future admin actions must require authentication and server-side authorisation.

## 3. Admin role is not part of MVP

Do not invent a public admin login or hardcoded admin password. If an admin portal is later required, define identity provider, roles, audit logs and secure server-side access before implementation.
