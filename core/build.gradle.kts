

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    id("hlandim.android.library")
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.hlandim.marvelheroes.core"
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