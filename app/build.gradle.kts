import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "devcon.learn.contacts"
    compileSdk = 35

    defaultConfig {
        applicationId = "devcon.learn.kakao.contacts"
        minSdk = 26
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
            val localProperties = Properties()
            localProperties.load(FileInputStream(rootProject.file("local.properties")))
            buildConfigField("String", "API_KEY", localProperties["REST_API_KEY"].toString())
            buildConfigField("String", "APP_KEY", localProperties["APP_KEY"].toString())
        }
        debug {
            val localProperties = Properties()
            localProperties.load(FileInputStream(rootProject.file("local.properties")))
            buildConfigField("String", "API_KEY", localProperties["REST_API_KEY"].toString())
            buildConfigField("String", "APP_KEY", localProperties["APP_KEY"].toString())
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
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
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("com.kakao.maps.open:android:2.12.8")
    implementation("com.kakao.sdk:v2-user:2.21.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
