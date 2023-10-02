plugins {
    id("hlandim.android.application")
}

android {
    namespace = "com.hlandim.marvelheroes"

    defaultConfig {
        applicationId = "com.hlandim.marvelheroes"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":feature:heroes"))
    implementation(project(":core:ui"))
    // Default
    api(libs.androidx.lifecycle.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.compose.bom))
    api(libs.androidx.compose.ui.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    testApi(libs.junit)
    androidTestApi(libs.androidx.test.ext.junit)
    androidTestApi(libs.androidx.test.espresso.espresso.core)
    androidTestApi(platform(libs.androidx.compose.compose.bom))
    androidTestApi(libs.androidx.compose.ui.ui.test.junit4)
    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(libs.androidx.compose.ui.ui.test.manifest)

    api(libs.androidx.core.core.ktx)
    api(libs.appcompat)
    api(libs.material)
    testApi(libs.junit4)
    androidTestApi(libs.androidx.test.ext.junit115)
    androidTestApi(libs.androidx.test.espresso.espresso.core)
}
