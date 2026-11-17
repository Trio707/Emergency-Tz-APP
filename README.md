# Emergency Tanzania

A native emergency dialer for Tanzania with one-tap access to Police (112), Fire & Rescue (114), and Ambulance (115).

Built with **Kotlin Multiplatform (KMP) + Compose Multiplatform** — single codebase, native Android (APK/AAB) and iOS (IPA) artifacts.

---

## Features

- **Three emergency services**: Police (112), Fire & Rescue (114), Ambulance (115)
- **One-tap dialing**: Direct emergency calls with minimal friction
- **Platform-appropriate UX**:
  - Android: Direct dial via `CALL_PHONE` permission (no confirmation dialog)
  - iOS: System call confirmation dialog (Apple policy)
- **Debug safety**: Debug builds cannot call real emergency numbers without explicit test number
- **Material 3 design**: Light/dark theme support
- **Offline-first**: No network required, numbers verified against official Tanzanian sources

---

## Emergency Numbers (Verified 2026-09-14)

| Service | Number | Source |
|---------|--------|--------|
| Police | 112 | [tanzania.go.tz](https://www.tanzania.go.tz/faqs), [polisi.go.tz](https://www.polisi.go.tz/mawasiliano/) |
| Fire & Rescue | 114 | [tanzania.go.tz](https://www.tanzania.go.tz/faqs) |
| Ambulance | 115 | [tanzania.go.tz](https://www.tanzania.go.tz/faqs) |

---

## Requirements

### Android
- Android Studio Koala (2024.1.1) or later
- **JDK 17 (standard, not Android Studio JBR 21)** — see [TOOLCHAIN.md](TOOLCHAIN.md)
- **Gradle 8.10.1** (wrapper) — **not 9.x**
- Android SDK 35
- Minimum SDK: 24 (Android 7.0)

### iOS
- macOS 14+ (Sonoma)
- Xcode 16+
- iOS 16+ deployment target
- Apple Developer Program membership ($99/yr) for device testing/TestFlight

---

## Quick Start

### 1. Clone & Configure

```bash
git clone https://github.com/Trio707/Emergency-Tz-APP.git
cd Emergency-Tz-APP

# Copy template and configure
cp local.properties.template local.properties
# Edit local.properties with your test number (optional)
```

### 2. Android Development

```bash
# Open in Android Studio
# Or build from command line:
./gradlew composeApp:assembleDebug

# Install on device:
./gradlew composeApp:installDebug
```

### 3. iOS Development

```bash
# Build the Compose framework for iOS
./gradlew composeApp:linkDebugFrameworkIosArm64

# Open iosApp/iosApp.xcodeproj in Xcode
# Build and run on simulator or device
```

---

## Debug Testing

### Android
Add a test phone number to `local.properties`:
```properties
DEBUG_TEST_PHONE_NUMBER=+255XXXXXXXXX
```

Debug builds will call this number instead of real emergency numbers. Release builds ignore this setting.

### iOS
iOS always shows a system confirmation dialog before placing calls — no test number needed for safety. The `tel://` URL scheme inherently requires user confirmation.

---

## Building for Release

### Android (AAB for Play Store)

```bash
# Configure signing in local.properties or CI secrets:
# KEYSTORE_PATH=path/to/keystore.jks
# KEYSTORE_PASSWORD=your_password
# KEY_ALIAS=your_alias
# KEY_PASSWORD=your_key_password

./gradlew composeApp:bundleRelease
# Output: composeApp/build/outputs/bundle/release/composeApp-release.aab
```

### iOS (IPA for TestFlight/App Store)

```bash
# In Xcode:
# 1. Select "Any iOS Device (arm64)" as destination
# 2. Product → Archive
# 3. Distribute App → TestFlight / App Store Connect
```

**CI/CD**: Both platforms build automatically on push to `main` via GitHub Actions. Configure secrets for signing:
- Android: `KEYSTORE_PATH`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`
- iOS: `IOS_CODE_SIGN_IDENTITY`, `IOS_PROVISIONING_PROFILE`

---

## Project Structure

```
Emergency-Tz-APP/
├── composeApp/                    # Shared Compose Multiplatform module
│   ├── src/
│   │   ├── commonMain/            # Shared code (90%+)
│   │   │   ├── kotlin/tz/emergency/app/
│   │   │   │   ├── EmergencyService.kt       # Domain model
│   │   │   │   ├── dialer/                   # Platform abstraction
│   │   │   │   │   ├── Dialer.kt             # expect class
│   │   │   │   │   ├── DialerFactory.kt      # Factory
│   │   │   │   │   ├── AndroidDialer.kt      # Android actual
│   │   │   │   │   └── IosDialer.kt          # iOS actual
│   │   │   │   ├── prefs/                    # Preferences abstraction
│   │   │   │   │   ├── Preferences.kt        # expect class
│   │   │   │   │   ├── PreferencesFactory.kt # Factory
│   │   │   │   │   ├── AndroidPreferences.kt # Android actual
│   │   │   │   │   └── IosPreferences.kt     # iOS actual
│   │   │   │   ├── ui/
│   │   │   │   │   ├── EmergencyTanzaniaApp.kt
│   │   │   │   │   ├── services/             # Button components
│   │   │   │   │   └── theme/                # Material 3 theme
│   │   │   │   └── composeResources/         # Icons, strings
│   │   │   ├── androidMain/                  # Android-only code
│   │   │   ├── iosMain/                      # iOS-only code
│   │   │   └── iosSimulatorArm64Main/        # iOS Simulator
│   │   └── build.gradle.kts
├── iosApp/                         # Xcode project (thin wrapper)
├── .github/workflows/              # CI/CD pipelines
├── gradle/libs.versions.toml       # Version catalog
├── build.gradle.kts                # Root build script
├── settings.gradle.kts             # Project inclusion
├── gradle.properties               # Version properties
├── local.properties.template       # Local config template
└── README.md
```

---

## Architecture

### Platform Abstractions

| Abstraction | Android Implementation | iOS Implementation |
|-------------|----------------------|-------------------|
| `Dialer` | `CALL_PHONE` + `Intent.ACTION_CALL` | `UIApplication.open(tel://)` |
| `Preferences` | `SharedPreferences` | `NSUserDefaults` |

### Key Design Decisions

1. **expect/actual pattern** for platform-specific code
2. **Compose Multiplatform** for shared UI (Material 3)
3. **No third-party dependencies** — only stdlib, Compose, AndroidX
4. **Debug safety** via `BuildConfig.DEBUG_TEST_PHONE_NUMBER` (Android) / inherent iOS prompt
5. **Single source of truth** for emergency numbers in `EmergencyService.kt`

---

## Version Alignment

| Component | Version |
|-----------|---------|
| Kotlin | 2.0.21 |
| Compose BOM | 2024.12.01 |
| Compose Multiplatform | 1.6.12 |
| AGP | 8.10.0 |
| **Gradle** | **8.10.1** |
| Xcode | 16.x |
| iOS Deployment Target | 16.0 |

📋 **Full toolchain details: [TOOLCHAIN.md](TOOLCHAIN.md)**

---

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `./gradlew check`
5. Submit a PR

---

## License

This project is for educational and emergency preparedness purposes. Emergency numbers are sourced from official Tanzanian government websites.

---

## Disclaimer

**This app is a tool to facilitate emergency contact. It does not guarantee emergency response. Always follow local emergency procedures. The developers are not liable for any failure to connect to emergency services.**

---

## Acknowledgments

- Tanzanian emergency service providers
- Kotlin & Compose Multiplatform teams
- Open source community