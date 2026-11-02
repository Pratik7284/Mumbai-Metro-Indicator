# Setup, Google Maps Configuration and APK Deployment

## 1. Prerequisites

- Google AI Studio account and access to Android Build workflow.
- Android Studio.
- Android SDK and a compatible JDK as required by the generated Gradle project.
- Android phone or emulator.
- Google Cloud project for Maps SDK configuration.
- Git.

## 2. Google AI Studio workflow

1. Open Google AI Studio and choose the Android app/build workflow.
2. Provide `MAIN_PROMPT.md` as the master specification.
3. Attach or paste the PRD, TRD, architecture, design and data dictionary as project context.
4. Ask the builder to implement in small phases: skeleton, Java route engine, local dataset, map, UI integration, tests.
5. Inspect generated language and build system. Do not assume it generated Java.
6. Export/download the project and open it in Android Studio.
7. Run Gradle sync and resolve all errors before adding more features.

## 3. Google Maps SDK setup

1. Create or select a Google Cloud project.
2. Enable Maps SDK for Android.
3. Configure billing as required by the current Google Maps Platform terms and pricing.
4. Create an Android API key.
5. Restrict the key to the app's package name and signing certificate SHA-1.
6. Add the key using the configuration mechanism recommended by the generated Android project.
7. Never commit a production API key to a public repository.
8. Test map rendering on an emulator/device.
9. Add a fallback UI when map tiles cannot load.

Google Maps SDK renders the map. It does not replace the application's own metro station dataset or graph route engine.

## 4. Build APK

In Android Studio:
1. Open the exported project.
2. Wait for Gradle sync.
3. Select the app run configuration.
4. Run on emulator or connected Android device.
5. Fix compilation/runtime errors.
6. For a debug build, use Build > Build Bundle(s) / APK(s) > Build APK(s), if available in the installed Android Studio version.
7. For release, use Build > Generate Signed Bundle / APK > APK and create/store a signing keystore securely.
8. Test the resulting APK on a physical Android device.

Gradle command commonly used for debug APK:
`./gradlew assembleDebug`

On Windows Command Prompt:
`gradlew.bat assembleDebug`

Typical output:
`app/build/outputs/apk/debug/app-debug.apk`

The actual output path may vary with project configuration.

## 5. Release checklist

- Verify all station data and operational status.
- Verify source links and timestamps.
- Check no demo values are shown as official.
- Confirm Maps key restrictions.
- Test airplane-mode route planning.
- Test on at least one physical Android phone.
- Store signing keystore and passwords safely; never upload them to Git.
- Include privacy notice and independent-app disclaimer.
