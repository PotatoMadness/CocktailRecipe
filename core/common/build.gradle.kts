plugins {
    id("cocktail.android.library")
}

android {
    namespace = "com.potatomadness.common"
}
dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.com.google.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}