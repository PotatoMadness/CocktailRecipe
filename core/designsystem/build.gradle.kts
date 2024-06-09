@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
    alias(libs.plugins.cocktail.compose)
}

android {
    namespace = "com.potatomadness.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.com.google.android.material)
}