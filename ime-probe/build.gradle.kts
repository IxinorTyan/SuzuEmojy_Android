plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "org.suzu.imeprobe"
    compileSdk = 34
    defaultConfig {
        applicationId = "org.suzu.imeprobe"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}
