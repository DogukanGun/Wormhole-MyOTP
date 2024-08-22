// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2") // Updated Gradle plugin
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48") // Latest Hilt version
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10") // Updated Kotlin plugin
    }
}
plugins {
    id("com.android.application") version "8.5.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "7.2.2" apply false
}
