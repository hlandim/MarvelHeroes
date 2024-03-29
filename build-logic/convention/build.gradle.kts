plugins {
    `kotlin-dsl`
}

group = "com.hlandim.marvelheroes.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

dependencies {
    implementation(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
    implementation(libs.org.jlleitschuh.gradle.ktlint.gradle)
    implementation(libs.io.gitlab.arturbosch.detekt.detekt.gradle.plugin)
    implementation(libs.hilt.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "hlandim.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "hlandim.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "hlandim.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "hlandim.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidLint") {
            id = "hlandim.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidDetekt") {
            id = "hlandim.android.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }
        register("androidFeature") {
            id = "hlandim.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidCompose") {
            id = "hlandim.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidUnitTest") {
            id = "hlandim.android.unitTest"
            implementationClass = "AndroidUnitTestConventionPlugin"
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("18"))
    }
}
