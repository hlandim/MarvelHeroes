import com.android.build.gradle.LibraryExtension
import com.hlandim.marvelheroes.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure(LibraryExtension::class.java) {
                configureAndroidCompose(this)
            }
        }
    }
}
