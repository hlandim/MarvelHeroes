package com.hlandim.marvelheroes

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.setDefaultConfig(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = Config.JAVA_VERSION
            targetCompatibility = Config.JAVA_VERSION
        }

        val libs = versionCatalog()
        dependencies {
            add("testImplementation", libs.findLibrary("junit4").get())
            add("coreLibraryDesugaring", libs.findLibrary("android-desugarJdkLibs").get())
        }
    }
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(Config.JVM_TARGET)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}

internal fun Project.versionCatalog(): VersionCatalog =
    extensions.getByType(VersionCatalogsExtension::class).named("libs")

