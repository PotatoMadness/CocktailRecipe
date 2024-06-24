@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
}

android {
    namespace = "com.potatomadness.data"
}

dependencies {
    api(project(":core:database"))
    api(project(":core:network"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
}