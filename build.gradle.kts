// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
//    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
//    id("com.android.application") version "8.2.2" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
//    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
}