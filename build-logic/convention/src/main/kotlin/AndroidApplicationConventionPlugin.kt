import com.android.build.api.dsl.ApplicationExtension
import com.hlandim.marvelheroes.Config.COMPILE_SDK
import com.hlandim.marvelheroes.Config.DEFAULT_TARGET
import com.hlandim.marvelheroes.Config.MIN_SDK
import com.hlandim.marvelheroes.configureAndroidCompose
import com.hlandim.marvelheroes.setDefaultConfig
import com.hlandim.marvelheroes.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = versionCatalog()
            with(pluginManager) {
                apply(libs.findPlugin("com-android-application").get().get().pluginId)
                apply(libs.findPlugin("org-jetbrains-kotlin-android").get().get().pluginId)
                apply("hlandim.android.lint")
                apply("hlandim.android.detekt")
                apply("hlandim.android.hilt")
                apply("hlandim.android.unitTest")
            }
            extensions.configure(ApplicationExtension::class.java) {
                compileSdk = COMPILE_SDK
                setDefaultConfig(this)
                configureAndroidCompose(this)
                with(defaultConfig) {
                    targetSdk = DEFAULT_TARGET
                    minSdk = MIN_SDK
                }
                buildTypes {
                    debug {
                        enableUnitTestCoverage = true
                    }
                }
            }
        }
    }
}
