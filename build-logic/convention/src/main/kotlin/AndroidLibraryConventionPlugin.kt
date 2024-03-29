import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.hlandim.marvelheroes.Config
import com.hlandim.marvelheroes.Config.COMPILE_SDK
import com.hlandim.marvelheroes.Config.DEFAULT_TARGET
import com.hlandim.marvelheroes.Config.MIN_SDK
import com.hlandim.marvelheroes.setDefaultConfig
import com.hlandim.marvelheroes.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = versionCatalog()
        with(pluginManager) {
            apply(libs.findPlugin("com-android-library").get().get().pluginId)
            apply(libs.findPlugin("org-jetbrains-kotlin-android").get().get().pluginId)
            apply("hlandim.android.lint")
            apply("hlandim.android.detekt")
            apply("hlandim.android.unitTest")
            apply(libs.findPlugin("org-jetbrains-kotlin-kapt").get().get().pluginId)
        }

        extensions.configure(LibraryExtension::class.java) {
            setDefaultConfig(this)
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = DEFAULT_TARGET
            buildTypes {
                debug {
                    enableUnitTestCoverage = true
                }
            }
        }
    }

    private fun configureKotlinAndroid(
        commonExtension: CommonExtension<*, *, *, *, *>,
    ) = with(commonExtension) {
        compileSdk = COMPILE_SDK

        defaultConfig {
            minSdk = MIN_SDK
        }

        kotlinOptions {
            jvmTarget = "${Config.JAVA_VERSION}"
        }

        buildFeatures {
            buildConfig = true
        }
    }

    private fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
        (this as ExtensionAware).extensions.configure("kotlinOptions", block)
    }
}
