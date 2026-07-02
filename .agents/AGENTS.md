# AI & Agent Guidelines for Android Kotlin Boilerplate

This document serves as instructions and rules for AI assistants, agents, and developers writing code or adding features to this repository.

## 🏗️ Architecture & Module Rules

This project follows a strict **Clean Architecture + Multi-Module MVVM** architecture. Do NOT violate the module dependency flow.

### Module Hierarchy & Rules
*   **`:core:common`** (Pure Kotlin): General utilities, extension functions, domain `AppResult<T>`, and `AppError`. Never depend on Android APIs or other modules.
*   **`:core:domain`** (Pure Kotlin): Core business logic. Contains `UseCase` base classes, domain model classes, and Repository interfaces. Depends only on `:core:common`.
*   **`:core:network`** (Android + Hilt): Retrofit, OkHttp configuration, error interceptors. Depends only on `:core:common`.
*   **`:core:data`** (Android + Hilt): Repository implementations, DataStore preferences, local databases. Depends on `:core:common`, `:core:domain`, and `:core:network`.
*   **`:core:ui`** (Compose): Design system, theme, typography, colors, and reusable composables. Depends on standard Compose and Lifecycle BOMs.
*   **`:core:testing`** (Android): Custom JUnit 5 rules and extensions (e.g., `MainDispatcherExtension`), mock utilities, and test fixtures. Should only be included as `testImplementation` or `androidTestImplementation`.
*   **`:feature:<name>`** (Compose + Hilt): Feature modules containing UI, ViewModels, and navigation integration. Depends on `:core:common`, `:core:domain`, and `:core:ui`. Do **NOT** depend on other feature modules or `:core:data`/`:core:network`.
*   **`:app`** (Android Application): Application entry point, Hilt initialization, `MainActivity`, root `NavHost` combining feature modules, and flavor configurations. Depends on all modules.

```
feature:home ──→ core:domain ──→ core:common
     │                │
     ├──→ core:ui     │
     │                ↓
     ↓          core:data ──→ core:network ──→ core:common
    app ──→ (all modules)
```

---

## 🎨 UI Guidelines (Jetpack Compose & Material 3)

*   **Compose Only**: Do not add XML layouts or ViewBinding. Use Compose for all new UI.
*   **Theme Consistency**: Always use `MaterialTheme.colorScheme` and `MaterialTheme.typography`. Do not use hardcoded colors or text styles.
*   **Edge-to-Edge**: Support edge-to-edge drawing. Use window insets (`Modifier.windowInsetsPadding`, `Modifier.safeDrawingPadding`) when appropriate.
*   **State Collection**: When collecting flows in UI, always use `collectAsStateWithLifecycle()` to respect the activity/fragment lifecycle.
*   **Preview Support**: Every UI screen/component should have a corresponding `@Preview` function with `BoilerplateTheme` wrapper. Use Preview Parameter Providers where necessary.

---

## ⚡ Concurrency & Data Flow Rules

*   **Coroutines & Flow**: Use Kotlin Coroutines and Flows for all async tasks. Never write RxJava code.
*   **Unidirectional Data Flow (UDF)**: ViewModels must expose a single `uiState` stream as a `StateFlow`.
*   **StateFlow Conversion**: Convert cold flows to hot state flows in ViewModel using:
    ```kotlin
    val uiState: StateFlow<HomeUiState> = repository.observeData()
        .map { ... }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )
    ```
*   **Errors**: Do not throw raw exceptions across domain boundaries. Return `AppResult.Error(AppError)` from domain layers. Use `runCatching` in repositories.

---

## 💉 Dependency Injection Rules (Hilt)

*   **Standard Scopes**: Cautiously select dependency scopes. Use `@Singleton` for core network/data helpers, but otherwise favor un-scoped or `@ActivityRetainedScoped`.
*   **Hilt ViewModels**: Inject ViewModels in Compose screens using `hiltViewModel()`. Do not instantiate ViewModels manually.
*   **Hilt Modules**: Organize modules clearly in a `di/` subpackage. Use `@Binds` for interface bindings and `@Provides` for instances (e.g. Retrofit, OkHttpClient).

---

## 🧪 Testing Rules

*   **JUnit 5**: We use JUnit 5. Test annotations must use `org.junit.jupiter.api.Test` (not JUnit 4 `@Test`).
*   **MockK**: Use `MockK` for mocking dependencies. Avoid Mockito.
*   **Turbine**: Use `Turbine` for testing Flows and StateFlows (`flow.test { ... }`).
*   **Coroutines Testing**: Annotate test classes requiring main thread dispatcher with `@ExtendWith(MainDispatcherExtension::class)`. Use `runTest` for suspend test methods.
*   **Assertion Consistency**: Use standard Kotlin test assertions (`assertEquals`, `assertIs`, `assertNull`, `assertTrue`).

---

## 📋 Steps for Common Code Generation Tasks

### 1. Adding a New Feature Module
1.  Create a subdirectory `feature/<name>`.
2.  Add `feature/<name>/build.gradle.kts` (copy from `feature/home/build.gradle.kts` and update the namespace).
3.  Include it in `settings.gradle.kts` via `include(":feature:<name>")`.
4.  Create your UI state `sealed interface <Name>UiState`.
5.  Create a `@HiltViewModel` named `<Name>ViewModel`.
6.  Create a Compose function `<Name>Screen`.
7.  Add screen navigation definitions in a `navigation` subpackage:
    ```kotlin
    const val <NAME>_ROUTE = "<name>"
    fun NavGraphBuilder.<name>Screen(...) { composable(<NAME>_ROUTE) { <Name>Screen(...) } }
    ```
8.  Register the route in `:app` module's `AppNavigation.kt`.

### 2. Adding a New Dependency
1.  Open `gradle/libs.versions.toml`.
2.  Add version in `[versions]` block.
3.  Define the library under `[libraries]` block.
4.  Add to a bundle under `[bundles]` block if sharing multiple related libraries.
5.  Sync project and inject into relevant module `build.gradle.kts` using catalog syntax (e.g., `libs.androidx.core.ktx`).

---

## 🛑 Coding Standards (Detekt & ktlint)

All code generated must compile cleanly and comply with the project formatting rules:
*   Lines must not exceed **120 characters**.
*   Indent size: **2 spaces**.
*   No unused imports or wildcard imports.
*   Use official Kotlin code style.
*   Run `./gradlew ktlintCheck detekt` to verify compliance before proposing changes.
