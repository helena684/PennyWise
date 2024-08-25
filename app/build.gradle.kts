val testImplementation: Unit
    get() {
        TODO()
    }

val implementation: Unit
    get() {
        TODO()
    }

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pennywise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pennywise"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.tasks)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation (libs.voghdev.pdfviewpager)

    implementation (libs.pdfviewer)
    implementation (libs.pdfviewpager)
    implementation (libs.mpandroidchart)
    implementation (libs.biometric)
    implementation (libs.gson)
    implementation (libs.circleimageview)
    implementation (libs.appcompat.v151)
    implementation (libs.material.v161)
    implementation (libs.constraintlayout)
    implementation (libs.itext7.core)
    implementation (libs.kernel)
    implementation (libs.layout)
    implementation (libs.mpandroidchart)
}