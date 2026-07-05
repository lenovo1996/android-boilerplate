plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.app.boilerplate"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.app.boilerplate"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = getNewVersionCode()
    versionName = getNewVersionName()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    getByName("debug") {
      // Debug signing config — generate your own keystore
      // keytool -genkey -v -keystore debug.keystore -alias debug -keyalg RSA -validity 10000
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
    debug {
      isMinifyEnabled = false
      applicationIdSuffix = ".debug"
    }
  }

  flavorDimensions += "environment"
  productFlavors {
    create("staging") {
      dimension = "environment"
      applicationIdSuffix = ".staging"
      versionNameSuffix = "-Staging"
      buildConfigField("String", "BASE_URL", "\"https://staging.api.example.com\"")
    }
    create("production") {
      dimension = "environment"
      buildConfigField("String", "BASE_URL", "\"https://api.example.com\"")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = libs.versions.jvmTarget.get()
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  lint {
    abortOnError = false
    checkDependencies = true
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      isReturnDefaultValues = true
    }
  }
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:network"))
  implementation(project(":core:domain"))
  implementation(project(":core:data"))
  implementation(project(":core:ui"))

  // Hilt
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.navigation.compose)

  // Compose
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.navigation)

  // AndroidX
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.splashscreen)

  // Logging
  implementation(libs.timber)

  // Debug
  debugImplementation(libs.leakcanary)
  debugImplementation(libs.bundles.compose.debug)

  // Testing
  testImplementation(project(":core:testing"))
  testImplementation(libs.bundles.testing)
  testRuntimeOnly(libs.junit5.engine)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(libs.compose.ui.test.junit4)
}

fun getNewVersionCode(): Int {
  return if (project.hasProperty("version_code")) {
    project.properties["version_code"].toString().toIntOrNull() ?: 1
  } else {
    1
  }
}

fun getNewVersionName(): String {
  return if (project.hasProperty("version_name")) {
    project.properties["version_name"].toString()
  } else {
    "1.0.0-dev"
  }
}
