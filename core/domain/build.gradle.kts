@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
}

android {
    namespace = "com.potatomadness.domain"
}

dependencies {
    api(project(":core:data"))
}