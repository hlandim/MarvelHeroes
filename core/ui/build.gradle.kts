plugins {
    id("hlandim.android.library")
    id("hlandim.android.compose")
}

android {
    namespace = "com.hlandim.marvelheroes.core.ui"
}

dependencies {
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.org.jetbrains.kotlinx.collections.immutable)

    debugApi(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.activity.compose)
}
