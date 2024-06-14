plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSerialization)

}

android {
    namespace = "com.santhoshkumar.storeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.santhoshkumar.storeapp"
        minSdk = 30
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        // ENABLING VIEWBINDING
        viewBinding = true
    }

    // BUILD OUTPUT CONFIGURATION
    applicationVariants.all {
        val variant = this
        variant.outputs.all {
            val output = this
            val appName = "StoreApp"
            val versionName = variant.versionName
            val newApkName = "$appName.v$versionName.apk"
            output as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName = newApkName
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // SPLASH SCREEN
    implementation(libs.androidx.core.splashscreen)

    // RETEROFIT AND HTTPCLIENT
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // KOTLIN SERIALIZATION
    implementation(libs.jetbrains.kotlinx.serialization.json)

    // COROUTINES
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //GLIDE FOR IMAGE
    implementation(libs.glide)

}