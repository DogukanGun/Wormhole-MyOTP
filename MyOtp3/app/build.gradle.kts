plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // KAPT for annotation processing
    id("com.google.dagger.hilt.android")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.dag.myotp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dag.myotp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures { // Enables Jetpack Compose for this module
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("io.metamask.androidsdk:metamask-android-sdk:0.2.1")
    //Hilt
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.49")
    //Hilt
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.49")
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.material3.android)
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //Compose
    implementation("androidx.compose.ui:ui:1.6.8")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.6.8")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.6.8")
    // Material Design
    implementation("androidx.compose.material:material:1.6.8")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.6.8")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.6.8")
    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.multidex:multidex:2.0.1")
    // Retrofit
    // from 2.7+  release changes the minimum requirements to Java 8+ or Android 5+.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Gson
    implementation("com.google.code.gson:gson:2.8.8")
    // Passkey
    implementation("androidx.credentials:credentials:1.2")
    // Android 13 and below.
    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
    //chucker
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")
    // network & serialization
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    //rxjava
    implementation("io.reactivex.rxjava2:rxjava:2.2.7")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.1")
}