plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.junit.plugin)
}

android {
    namespace = "com.ot.booklist"
    compileSdk =
        libs.versions.compileSdkVersion
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdkVersion
                .get()
                .toInt()

        testInstrumentationRunner = "org.junit.runners.JUnit4"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // moshi
    implementation(libs.moshi)

    /* ideally should use ksp but dagger support for ksp is really bad at this time.
    Prefer kotlin-inject or manual di and use ksp for this.*/
    kapt(libs.moshi.codegen)

    // glide
    implementation(libs.glide)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi.converter)

    // dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.window)
    implementation(libs.windowsize)

    implementation(libs.material)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.runner)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core)
}
