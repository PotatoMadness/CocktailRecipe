@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
    alias(libs.plugins.cocktail.room)
}

android {
    namespace = "com.potatomadness.domain"
}

dependencies {
    implementation(project(":core:data"))
}