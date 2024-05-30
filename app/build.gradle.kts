@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.application)
}
android {
    namespace = "com.potatomadness.cocktail"

    defaultConfig {
        applicationId = "com.potatomadness.cocktail"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.material3.window.size)
    implementation(libs.appcompat)

    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.window)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.glide.compose)
    implementation(libs.glide.annotations)

    testImplementation(libs.junit)

    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":feature:home"))
    implementation(project(":feature:favorites"))
    implementation(project(":feature:myrecipe"))
    implementation(project(":feature:detail"))
}