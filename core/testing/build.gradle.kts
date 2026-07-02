plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.app.core.testing"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
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

  api(libs.junit5.api)
  api(libs.junit5.params)
  api(libs.mockk)
  api(libs.kotlin.test)
  api(libs.kotlinx.coroutines.test)
  api(libs.turbine)
}
