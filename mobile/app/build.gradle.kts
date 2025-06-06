plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.uken.motovault"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.uken.motovault"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Unit testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Retrofit & Gson for testing
    testImplementation("com.squareup.retrofit2:retrofit:2.9.0")
    testImplementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // MockWebServer to mock HTTP responses
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // Mockito for mocking dependencies (if needed)
    testImplementation("org.mockito:mockito-core:5.8.0")

    // WorkManager dependency
    implementation("androidx.work:work-runtime-ktx:2.8.0")

    // Compose material 3
    implementation("androidx.compose.material3:material3:1.1.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Text recognition
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("androidx.activity:activity:1.7.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.google.mlkit:text-recognition:16.0.1")

    // Charts library https://github.com/dautovicharis/Charts
    val chartsVersion = "1.2.0"
    implementation("com.github.dautovicharis:Charts:$chartsVersion")

    // Apache POI for spreadsheet generation
    val poiVersion = "5.2.3"
    implementation("org.apache.poi:poi:$poiVersion")
    implementation("org.apache.poi:poi-ooxml:$poiVersion")

    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.lifecycle.viewmodel.compose.android)
    implementation(libs.coil.compose)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}