plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.ktlint)
  alias(libs.plugins.detekt)
  alias(libs.plugins.sonarqube)
}

allprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  ktlint {
    android.set(true)
    verbose.set(true)
    outputToConsole.set(true)
  }
}

subprojects {
  apply(plugin = "io.gitlab.arturbosch.detekt")

  detekt {
    config.setFrom(rootProject.files("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    allRules = false
  }

  tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
      events("passed", "failed", "skipped")
      exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
  }
}

sonarqube {
  properties {
    property("sonar.organization", "celerik")
    property("sonar.projectKey", "celerik_android-kotlin-boilerplate")
    property("sonar.host.url", "https://sonarcloud.io")
    property("sonar.sourceEncoding", "UTF-8")
  }
}

tasks.register("clean", Delete::class.java) {
  delete(rootProject.layout.buildDirectory)
}
