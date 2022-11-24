buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependency.Hilt.HILT_PLUGIN)
    }
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.presentation"
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(Dependency.KTX.CORE)
    implementation(Dependency.AndroidX.APP_COMPAT)
    implementation(Dependency.AndroidX.MATERIAL)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependency.Nav.NAV_FRAGMENT)
    implementation(Dependency.Nav.NAV_UI)
    testImplementation(Dependency.Test.JUNIT)
    androidTestImplementation(Dependency.AndroidTest.TEST_RUNNER)
    androidTestImplementation(Dependency.AndroidTest.ESPRESSO_CORE)

    implementation(Dependency.Hilt.HILT)
    kapt(Dependency.Hilt.HILT_KAPT)

    implementation(Dependency.Log.TIMBER)

    testImplementation(Dependency.Test.TRUTH)
    testImplementation(Dependency.Test.MOCKITO)
    testImplementation(Dependency.Test.CORE_TEST)
    testImplementation(Dependency.Test.MOCKK)

    implementation(Dependency.Remote.RETROFIT)
    implementation(Dependency.Remote.CONVERTER)
    implementation(Dependency.Remote.HTTP)

    implementation(Dependency.Coroutine.COROUTINE_CORE)
    implementation(Dependency.Coroutine.ANDROID)
    testImplementation(Dependency.Coroutine.TEST)

    implementation(Dependency.Room.RUNTIME)
    kapt(Dependency.Room.COMPILER)
    implementation(Dependency.Room.ROOM_KTX)

    implementation(Dependency.LifeCycle.VM)
    implementation(Dependency.LifeCycle.EXTENSIONS)
    implementation(Dependency.LifeCycle.LIVEDATA)

    implementation(Dependency.Paging.PAGING)

    implementation(Dependency.Glide.GLIDE)
    implementation(Dependency.Glide.GLIDE_COMPILER)

    implementation(Dependency.Nav.NAV_UI)
    implementation(Dependency.Nav.NAV_FRAGMENT)
}