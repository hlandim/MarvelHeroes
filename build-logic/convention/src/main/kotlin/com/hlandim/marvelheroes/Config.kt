package com.hlandim.marvelheroes

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {
    const val DEFAULT_TARGET = 34
    const val COMPILE_SDK = 34
    const val MIN_SDK = 29
    val JAVA_VERSION = JavaVersion.VERSION_17
    val JVM_TARGET = JvmTarget.JVM_17
}
