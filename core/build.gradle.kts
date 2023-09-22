import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

fun properties(propertiesFile: String) = Properties().apply {
    runCatching { load(FileInputStream(rootProject.file(propertiesFile))) }
}

val localProperties = properties("local.properties")
android {
    namespace = "com.hlandim.marvelheroes.core"
    compileSdk = 34

    defaultConfig {
        buildConfigField(
            String::class.java.name, "MARVEL_PUBLIC_API_KEY",
            "\"" + localProperties.getProperty("MARVEL_PUBLIC_API_KEY", "") + "\"",
        )
        buildConfigField(
            String::class.java.name, "MARVEL_PRIVATE_API_KEY",
            "\"" + localProperties.getProperty("MARVEL_PRIVATE_API_KEY", "") + "\"",
        )
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }
    kotlinOptions {
        jvmTarget = "20"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Default
    api(libs.androidx.lifecycle.lifecycle.runtime.ktx)
    api(libs.androidx.activity.activity.compose)
    api(platform(libs.androidx.compose.compose.bom))
    api(libs.androidx.compose.ui.ui.graphics)
    api(libs.androidx.compose.ui.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    testApi(libs.junit)
    androidTestApi(libs.androidx.test.ext.junit)
    androidTestApi(libs.androidx.test.espresso.espresso.core)
    androidTestApi(platform(libs.androidx.compose.compose.bom))
    androidTestApi(libs.androidx.compose.ui.ui.test.junit4)
    debugApi(libs.androidx.compose.ui.ui.tooling)
    debugApi(libs.androidx.compose.ui.ui.test.manifest)

    api(libs.androidx.core.core.ktx)
    api(libs.appcompat)
    api(libs.material)
    testApi(libs.junit.junit)
    androidTestApi(libs.androidx.test.ext.junit115)
    androidTestApi(libs.androidx.test.espresso.espresso.core)

    // Room
    api(libs.androidx.room.room.runitme)
    annotationProcessor(libs.androidx.room.room.compiler)

    //Retrofit
    api(libs.com.squareup.retrofit2.retrofit)
    api(libs.com.squareup.retrofit2.retrofit.converter.moshi)
    api(libs.com.squareup.okhttp3.logging.interceptor)

    // Hilt
    api(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
}