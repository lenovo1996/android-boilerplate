plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.app.core.network"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = libs.versions.jvmTarget.get()
  }

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  implementation(project(":core:common"))

  implementation(libs.bundles.network)
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.timber)

  testImplementation(libs.bundles.testing)
  testImplementation(libs.okhttp.mockwebserver)
  testRuntimeOnly(libs.junit5.engine)
}
