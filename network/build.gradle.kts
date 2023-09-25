plugins {
    id("hlandim.android.library")
}

android {
    namespace = "com.hlandim.marvelheroes.network"
}

//plugins {
//
////    alias(libs.plugins.com.android.library)
//    alias(libs.plugins.org.jetbrains.kotlin.android)
//    alias(libs.plugins.org.jetbrains.kotlin.kapt)
//}
//
//
//fun properties(propertiesFile: String) = Properties().apply {
//    runCatching { load(FileInputStream(rootProject.file(propertiesFile))) }
//}
//
//val localProperties = properties("local.properties")
//android {
//    namespace = "com.hlandim.marvelheroes.network"
//
//    defaultConfig {
//        buildConfigField(
//            String::class.java.name, "MARVEL_PUBLIC_API_KEY",
//            "\"" + localProperties.getProperty("MARVEL_PUBLIC_API_KEY", "") + "\"",
//        )
//        buildConfigField(
//            String::class.java.name, "MARVEL_PRIVATE_API_KEY",
//            "\"" + localProperties.getProperty("MARVEL_PRIVATE_API_KEY", "") + "\"",
//        )
//    }
//}

dependencies {
    implementation(project(":core"))
}