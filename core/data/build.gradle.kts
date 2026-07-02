plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.app.core.data"
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
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:domain"))
  implementation(project(":core:network"))

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.androidx.datastore.preferences)
  implementation(libs.timber)

  testImplementation(project(":core:testing"))
  testImplementation(libs.bundles.testing)
  testRuntimeOnly(libs.junit5.engine)
}
