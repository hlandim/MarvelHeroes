package com.hlandim.marvelheroes.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val DEFAULT_TARGET = 33
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure(LibraryExtension::class.java) {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = DEFAULT_TARGET
        }
    }
}