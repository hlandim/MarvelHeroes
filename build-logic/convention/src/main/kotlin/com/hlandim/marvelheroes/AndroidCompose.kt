package com.hlandim.marvelheroes

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidx-compose-compile-version").get().toString()
        }

        kotlinOptions {
            jvmTarget = "${Config.JAVA_VERSION}"
        }
        dependencies {
            val bom = libs.findLibrary("androidx-compose-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            // Android Studio Preview support
            add("implementation", libs.findLibrary("androidx-compose-ui-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-ui-tooling").get())
            // Material
            add("implementation", libs.findLibrary("androidx-compose-material3").get())
        }
    }
}

private fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
