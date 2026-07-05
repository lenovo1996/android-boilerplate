# Android Kotlin Boilerplate

A modern Android project template with **multi-module architecture**, **MVVM**, and production-ready tooling.

## Tech Stack

| Category | Technology |
|---|---|
| **Language** | Kotlin 2.1 |
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | Multi-module MVVM + Clean Architecture |
| **DI** | Hilt |
| **Async** | Coroutines + Flow |
| **Networking** | Retrofit + OkHttp + Kotlinx Serialization |
| **Navigation** | Compose Navigation |
| **Local Storage** | DataStore Preferences |
| **Image Loading** | Coil |
| **Logging** | Timber |
| **Testing** | JUnit 5 + MockK + Turbine |
| **Code Quality** | ktlint + Detekt |
| **CI/CD** | GitHub Actions + Fastlane |
| **Build System** | Gradle 8.11 + Version Catalog |

## Module Structure

```
├── app                  Application entry point, navigation, DI
└── core/
    ├── common           Pure Kotlin: Result type, extensions, models
    ├── domain           Use cases, repository interfaces
    ├── data             Repository implementations, DataStore
    ├── network          Retrofit, OkHttp, interceptors
    ├── ui               Compose theme, design system, components
    └── testing          Test utilities, JUnit extensions
```

### Dependency Graph

```
core:domain ──→ core:common
     │
     ↓
 core:data ──→ core:network ──→ core:common
     ↑
  core:ui

app ──→ (all modules)
```

## Getting Started

### Prerequisites
- Android Studio Ladybug (2024.2) or later
- JDK 17
- Android SDK 35

### Setup
```bash
git clone https://github.com/celerik/android-kotlin-boilerplate.git
cd android-kotlin-boilerplate
```

Open in Android Studio and sync Gradle.

### Build Variants

| Environment | Build Type | Variant |
|---|---|---|
| Staging | Debug | `stagingDebug` |
| Staging | Release | `stagingRelease` |
| Production | Debug | `productionDebug` |
| Production | Release | `productionRelease` |

### Run

```bash
# Debug build
./gradlew assembleStagingDebug

# Run all tests
./gradlew test

# Run linters
./gradlew ktlintCheck detekt
```

## Creating a New Feature Module

1. Create `feature/<name>/build.gradle.kts` (copy from `core/ui/build.gradle.kts` and adjust)
2. Add `include(":feature:<name>")` to `settings.gradle.kts`
3. Create your screen, ViewModel, and UI state
4. Add navigation extensions in `feature/<name>/navigation/`
5. Register the navigation in `app/.../navigation/AppNavigation.kt`

## Adding a New API Service

1. Define the Retrofit interface in `core/network/`
2. Add it to `NetworkModule` as a `@Provides` function
3. Create a repository interface in `core/domain/`
4. Implement it in `core/data/` and bind via `DataModule`
5. Create a use case in `core/domain/`

## CI/CD

- **PR Checks**: Runs ktlint, detekt, and unit tests on every pull request
- **Distribution**: Tags matching `v*.*.*-Beta` trigger staging APK build and Firebase App Distribution

## License

This project is available under the MIT License.
