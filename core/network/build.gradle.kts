import java.io.FileInputStream
import java.util.Properties

plugins {
    id("hlandim.android.library")
    id("hlandim.android.hilt")
    id("hlandim.android.unitTest")
}
fun properties(propertiesFile: String) = Properties().apply {
    runCatching { load(FileInputStream(rootProject.file(propertiesFile))) }
}

val localProperties = properties("local.properties")
android {
    namespace = "com.hlandim.marvelheroes.core.network"

    defaultConfig {
        buildConfigField(
            String::class.java.name,
            "MARVEL_PUBLIC_API_KEY",
            "\"" + localProperties.getProperty("MARVEL_PUBLIC_API_KEY", "") + "\""
        )
        buildConfigField(
            String::class.java.name,
            "MARVEL_PRIVATE_API_KEY",
            "\"" + localProperties.getProperty("MARVEL_PRIVATE_API_KEY", "") + "\""
        )
    }
}

dependencies {
    // Retrofit
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.retrofit.converter.moshi)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
}
