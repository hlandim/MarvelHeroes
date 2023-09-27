import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies

private const val HILT_COMPILE_LIB = "hilt.compiler"
class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
            with(pluginManager) {
                apply(libs.findPlugin("com-google-dagger-hilt-android").get().get().pluginId)
                // KAPT must go last to avoid build warnings.
                // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
                apply(libs.findPlugin("org-jetbrains-kotlin-kapt").get().get().pluginId)
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt.android").get())
                "kaptAndroidTest"(libs.findLibrary(HILT_COMPILE_LIB).get())
                "kaptTest"(libs.findLibrary(HILT_COMPILE_LIB).get())
                "kapt"(libs.findLibrary(HILT_COMPILE_LIB).get())
            }
        }
    }

}
