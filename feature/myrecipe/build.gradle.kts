@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.feature)
}

android {
    namespace = "com.potatomadness.myrecipe"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
}