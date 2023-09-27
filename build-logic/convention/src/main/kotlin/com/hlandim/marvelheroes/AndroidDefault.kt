package com.hlandim.marvelheroes

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal fun Project.setDefaultConfig(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = Config.JAVA_VERSION
            targetCompatibility = Config.JAVA_VERSION
        }

        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        dependencies {
            add("testImplementation", libs.findLibrary("junit4").get())
            add("coreLibraryDesugaring", libs.findLibrary("android-desugarJdkLibs").get())
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = Config.JAVA_VERSION.toString()
        }
    }
}
