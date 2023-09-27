plugins {
    id("hlandim.android.library")
    id("hlandim.android.hilt")
    id("hlandim.android.room")
}

android {
    namespace = "com.hlandim.marvelheroes.core.database"
}

dependencies {
    implementation(project(":core:model"))
}
