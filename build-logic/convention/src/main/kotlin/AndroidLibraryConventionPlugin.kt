import com.android.build.gradle.LibraryExtension
import com.potatomadness.convention.configureHiltAndroid
import com.potatomadness.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid()
                configureHiltAndroid()
                defaultConfig.targetSdk = 34
                testOptions.animationsDisabled = true
            }
        }
    }
}