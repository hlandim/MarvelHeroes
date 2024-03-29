import com.android.build.gradle.LibraryExtension
import com.hlandim.marvelheroes.configureAndroidCompose
import com.hlandim.marvelheroes.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("hlandim.android.library")
                apply("hlandim.android.hilt")
            }

            extensions.configure(LibraryExtension::class.java) {
                configureAndroidCompose(this)
            }

            dependencies {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:ui"))
                add("implementation", versionCatalog().findLibrary("hilt-compose").get())
            }
        }
    }
}
