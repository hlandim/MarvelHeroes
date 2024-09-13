import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.hlandim.marvelheroes.Config.COMPILE_SDK
import com.hlandim.marvelheroes.Config.DEFAULT_TARGET
import com.hlandim.marvelheroes.Config.MIN_SDK
import com.hlandim.marvelheroes.setDefaultConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("hlandim.android.lint")
            apply("hlandim.android.detekt")
            apply("hlandim.android.unitTest")
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
        commonExtension: CommonExtension<*, *, *, *, *, *>,
    ) = with(commonExtension) {
        compileSdk = COMPILE_SDK

        defaultConfig {
            minSdk = MIN_SDK
        }

        buildFeatures {
            buildConfig = true
        }
    }
}
