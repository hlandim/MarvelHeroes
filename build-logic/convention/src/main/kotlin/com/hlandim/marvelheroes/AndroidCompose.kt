package com.hlandim.marvelheroes

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val libs = versionCatalog()
    with(pluginManager) {
        apply(libs.findPlugin("compose-compiler").get().get().pluginId)
        apply(libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId)
    }
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidx-compose-compile-version").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

            add("implementation", libs.findLibrary("androidx.navigation.navigation.compose").get())
            add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
            add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
        }
    }
}
