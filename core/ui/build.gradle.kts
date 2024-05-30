@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
    alias(libs.plugins.cocktail.compose)
}

android {
    namespace = "com.potatomadness.ui"
}

dependencies {
    implementation(project(":core:data"))
}