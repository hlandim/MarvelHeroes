import com.hlandim.marvelheroes.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class AndroidUnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = versionCatalog()

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }

            dependencies {
                add("testImplementation", libs.findLibrary("io-mockk").get())
                add("testImplementation", libs.findLibrary("io-kotest-kotest-runner-junit5").get())
                add("testImplementation", libs.findLibrary("org-junit-jupiter-junit-api").get())
                add(
                    "testImplementation",
                    libs.findLibrary("org-junit-jupiter-junit-jupiter-engine").get()
                )
                add(
                    "testImplementation",
                    libs.findLibrary("org-junit-jupiter-junit-jupiter-vintage").get()
                )
            }
        }
    }
}
