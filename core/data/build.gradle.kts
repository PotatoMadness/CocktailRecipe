@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cocktail.library)
}

android {
    namespace = "com.potatomadness.data"
}

dependencies {
    api(project(":core:database"))
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp.logging)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
}