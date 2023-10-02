plugins {
    id("hlandim.android.library")
    id("hlandim.android.koin")
}

android {
    namespace = "com.hlandim.marvelheroes.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(libs.com.squareup.retrofit2.retrofit)
}
