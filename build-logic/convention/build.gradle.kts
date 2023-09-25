plugins {
    `kotlin-dsl`
}

group = "com.hlandim.marvelheroes.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

dependencies {
    implementation(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
}

gradlePlugin{
    plugins{
        register("androidLibrary"){
            id="hlandim.android.library"
            implementationClass="com.hlandim.marvelheroes.buildlogic.AndroidLibraryConventionPlugin"
        }
    }
}