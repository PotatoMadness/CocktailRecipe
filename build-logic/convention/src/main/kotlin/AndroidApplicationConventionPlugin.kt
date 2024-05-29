import com.android.build.api.dsl.ApplicationExtension
import com.potatomadness.convention.configureComposeAndroid
import com.potatomadness.convention.configureHiltAndroid
import com.potatomadness.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.dropbox.dependency-guard")
            }

            extensions.configure<ApplicationExtension> {
                configureComposeAndroid()
                configureHiltAndroid()
                configureKotlinAndroid()
                defaultConfig.targetSdk = 34
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
            }
        }
    }
}
