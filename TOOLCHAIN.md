# Emergency Tanzania — Toolchain Requirements

This document specifies the exact toolchain versions required to build the Emergency Tanzania KMP + Compose Multiplatform project.

---

## Required Toolchain Matrix

| Component | Required Version | Notes |
|-----------|-----------------|-------|
| **JDK** | **17** (LTS) — **not** Android Studio JBR 21 | Oracle JDK, Eclipse Temurin, Azul Zulu, or Microsoft Build of OpenJDK. **Must be JDK 17, not 21+.** |
| **Gradle Wrapper** | **8.10.1** | Do not upgrade to 9.x. Incompatible with current AGP. |
| **Android Gradle Plugin (AGP)** | **8.10.0** | Must match Gradle 8.10.1. Do not use 8.11.0+. |
| **Kotlin** | **2.0.21** | Bundled with AGP 8.10.0. |
| **Compose Multiplatform** | **1.6.12** | Must match Kotlin 2.0.21. |
| **Compose BOM** | **2024.12.01** | AndroidX Compose libraries. |
| **Android SDK** | **compileSdk 35**, **targetSdk 35**, **minSdk 24** | |
| **Xcode (iOS)** | **16.x** | iOS 18 SDK, macOS 14+ (Sonoma). |
| **Kotlin Multiplatform** | **2.0.21** | Same as Kotlin version. |

---

## Why These Specific Versions?

### JDK 17 (Critical)
- **Android Studio JBR 21 (25.0.3) is NOT supported** by Kotlin 2.0.21 / Gradle 8.10.1
- The Kotlin compiler bundled with Gradle 8.10.1 fails with `IllegalArgumentException: 25.0.3` when run on JBR 21
- Use a **standard JDK 17 distribution** (Temurin, Zulu, Microsoft, Oracle)

### Gradle 8.10.1 + AGP 8.10.0 (Critical)
- AGP 8.10.0 is the **last AGP version compatible with Gradle 8.x**
- AGP 8.11.0+ requires Gradle 9.x
- Gradle 9.x bundles Kotlin 2.1.x which requires different Compose Multiplatform versions
- The `DefaultArtifactPublicationSet` error occurs when mixing Gradle 9.x with AGP ≤ 8.12.0

### Compose Multiplatform 1.6.12
- Must match Kotlin 2.0.21 exactly
- Version 1.7.0+ requires Kotlin 2.1.x (Gradle 9.x)

---

## Local Development Setup

### 1. Install JDK 17

**Windows (PowerShell):**
```powershell
# Using winget
winget install EclipseAdoptium.Temurin.17.JDK

# Or Chocolatey
choco install temurin17jdk

# Or download from https://adoptium.net/temurin/releases/?version=17
```

**macOS/Linux:**
```bash
# Homebrew
brew install --cask temurin@17

# SDKMAN
sdk install java 17.0.12-tem

# Or download from https://adoptium.net/
```

### 2. Verify JAVA_HOME
```bash
# Should point to JDK 17 installation, NOT Android Studio JBR
echo $JAVA_HOME
# Example: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home

java -version
# openjdk version "17.0.12" 2024-07-16
# OpenJDK Runtime Environment Temurin-17.0.12+7
# OpenJDK 64-Bit Server VM Temurin-17.0.12+7
```

### 3. Android SDK
```bash
# Install via Android Studio or command line
# Required: Platform 35, Build Tools 35.0.0, Platform Tools

# Set ANDROID_HOME
export ANDROID_HOME=$HOME/Library/Android/sdk  # macOS/Linux
# or
$env:ANDROID_HOME = "$env:USERPROFILE\AppData\Local\Android\Sdk"  # Windows
```

### 4. Build Commands

```bash
# Clone
git clone https://github.com/Trio707/Emergency-Tz-APP.git
cd Emergency-Tz-APP

# Configure local.properties (optional, for debug test number)
cp local.properties.template local.properties
# Edit local.properties and add: DEBUG_TEST_PHONE_NUMBER=+255XXXXXXXXX

# Build debug APK
./gradlew composeApp:assembleDebug

# Build release AAB (requires signing config)
./gradlew composeApp:bundleRelease

# Build iOS framework (macOS only)
./gradlew composeApp:linkDebugFrameworkIosArm64
```

---

## CI/CD Environment (GitHub Actions)

```yaml
# .github/workflows/android.yml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
          cache: 'gradle'
      - name: Build Debug APK
        run: ./gradlew composeApp:assembleDebug
```

```yaml
# .github/workflows/ios.yml
jobs:
  build:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
          cache: 'gradle'
      - uses: maxim-lobanov/setup-xcode@v1
        with:
          xcode-version: '16.0'
      - name: Build iOS Framework
        run: ./gradlew composeApp:linkDebugFrameworkIosArm64
```

---

## Troubleshooting

| Error | Cause | Fix |
|-------|-------|-----|
| `IllegalArgumentException: 25.0.3` | Running on JBR 21 | Set `JAVA_HOME` to JDK 17 |
| `DefaultArtifactPublicationSet` | Gradle 9.x + AGP ≤ 8.12.0 | Use Gradle 8.10.1 |
| `Plugin not found: org.jetbrains.compose` | Missing JetBrains repo | Ensure `maven.pkg.jetbrains.space/public/p/compose/dev` in `pluginManagement.repositories` |
| `Cannot add extension 'kotlin'` | Both `kotlin("multiplatform")` and `kotlin-android` applied | Use only `id("org.jetbrains.kotlin.multiplatform")` |
| `Missing androidTarget()` | `com.android.application` without `androidTarget()` | Add `androidTarget()` in `kotlin { }` block |

---

## Version Upgrade Path (Future)

When upgrading, **all components must move together**:

```
JDK 17 → 21 (when Kotlin 2.1.x supported)
Gradle 8.10.1 → 9.x
AGP 8.10.0 → 8.13+
Kotlin 2.0.21 → 2.1.x
Compose Multiplatform 1.6.12 → 1.7.x
Compose BOM 2024.12.01 → 2025.xx.xx
```

**Never mix versions across rows.**

---

## Quick Reference: File Versions

```toml
# gradle/libs.versions.toml
[versions]
agp = "8.10.0"
kotlin = "2.0.21"
composeBom = "2024.12.01"
composeMultiplatform = "1.6.12"
coreKtx = "1.15.0"
activityCompose = "1.10.0"
```

```properties
# gradle/wrapper/gradle-wrapper.properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.10.1-bin.zip
```

```properties
# gradle.properties
org.gradle.java.home=/path/to/jdk-17  # Set locally, not in repo
kotlin.mpp.androidGradlePluginCompatibility.nowarn=true
```

---

*Last updated: 2026-11-17*
*Validated against: Kotlin 2.0.21, AGP 8.10.0, Gradle 8.10.1, JDK 17*