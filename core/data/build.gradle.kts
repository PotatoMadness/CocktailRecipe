@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
    alias(libs.plugins.cocktail.room)
}

android {
    namespace = "com.potatomadness.data"
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp.logging)
}