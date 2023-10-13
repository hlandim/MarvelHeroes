pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MarvelHeroes"
include(":app")
include(":core")
include(":core:data")
include(":core:database")
include(":core:model")
include(":core:network")
include(":feature")
include(":core:testing")
include(":core:ui")
include(":feature:heroes-list")
include(":feature:hero-details")
include(":feature:comics-list")
