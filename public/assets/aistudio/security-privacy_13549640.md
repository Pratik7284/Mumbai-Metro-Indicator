# Security and Privacy Requirements

## 1. Data minimisation

- No login required for MVP.
- Store favourites and recent searches locally.
- Do not collect personal data unless a feature genuinely requires it.
- Location access is optional; ask only when implementing a nearby-station feature.
- Explain why location is needed before requesting permission.
- Provide a way to clear local search history.

## 2. Google Maps API key

- Use a dedicated Android Maps API key.
- Restrict the key to the Android application package name and signing certificate SHA-1.
- Enable only the APIs required by the application.
- Do not commit unrestricted keys to Git.
- Follow current Google Maps Platform billing, quota and terms.
- If a server-side API is added, keep server credentials on the server.

## 3. Network security

- Use HTTPS for remote sources.
- Validate downloaded dataset versions and structure.
- Reject malformed or unexpected data.
- Cache the last known-good dataset.
- Do not execute downloaded code.

## 4. Location

- Manual station search must work without location permission.
- If location is added, use it only for the stated feature.
- Do not store precise location history unless essential and clearly disclosed.

## 5. Third-party dependencies

- Record library names, versions and licences.
- Keep dependencies updated.
- Review permissions requested by dependencies.
- Do not copy source code from repositories without checking licence and attribution requirements.

## 6. Disclaimer

Metro Mumbai is an independent informational project and is not an official app of MMRDA or any metro operator. Data may be delayed or incomplete. Passengers should follow official operator announcements and station instructions.
