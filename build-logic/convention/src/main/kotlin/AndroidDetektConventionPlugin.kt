import com.hlandim.marvelheroes.Config
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class AndroidDetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure(DetektExtension::class.java) {
                detekt {
                    baseline = file("$rootDir/config/detekt/baseline.xml")
                    buildUponDefaultConfig = true
                    config.setFrom(files("$rootDir/config/default-detekt-config.yml"))
                    source.setFrom(
                        objects.fileCollection().from(
                            DetektExtension.DEFAULT_SRC_DIR_JAVA,
                            DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
                            DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
                            DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
                        )
                    )
                }
            }

            tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
                jvmTarget = "${Config.JAVA_VERSION}"
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    txt.required.set(true)
                    sarif.required.set(true)
                }
            }
            tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
                jvmTarget = "${Config.JAVA_VERSION}"
            }

            dependencies {
                "detektPlugins"(libs.findLibrary("io-gitlab-arturbosch-detekt-detekt-formatting").get())
            }
        }
    }

    private fun Project.detekt(configure: Action<DetektExtension>): Unit =
        (this as ExtensionAware).extensions.configure("detekt", configure)
}
