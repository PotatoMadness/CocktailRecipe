// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.google.service) apply false
    alias(libs.plugins.firebase.cryshlytics) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.room) apply false
}