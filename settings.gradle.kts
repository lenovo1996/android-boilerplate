pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "boilerplate"

include(":app")
include(":core:common")
include(":core:network")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":core:testing")
include(":feature:home")
