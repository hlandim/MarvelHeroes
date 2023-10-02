import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies

class AndroidKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

            dependencies {
                "implementation"(libs.findLibrary("io-insert-koin-core").get())
                "implementation"(libs.findLibrary("io-insert-koin-android").get())
                "implementation"(libs.findLibrary("io-insert-koin-androidx-navigation").get())
            }
        }
    }
}
