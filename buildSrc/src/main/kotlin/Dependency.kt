object Dependency {

    object Kotlin {
        const val SDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21"
    }

    object AndroidX {
        const val MATERIAL = "com.google.android.material:material:1.6.1"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val APP_COMPAT = "androidx.appcompat:appcompat:1.5.1"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:1.7.0"
    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
        const val ANDROID_JUNIT_RUNNER = "AndroidJUnitRunner"
        const val TRUTH = "com.google.truth:truth:1.0"
        const val MOCKITO = "org.mockito:mockito-inline:3.4.0"
        const val CORE_TEST = "android.arch.core:core-testing:1.1.1"
        const val MOCKK = "io.mockk:mockk:1.12.4"
    }

    object AndroidTest {
        const val TEST_RUNNER = "androidx.test.ext:junit:1.1.3"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Hilt {
        const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:2.43.2"

        const val HILT = "com.google.dagger:hilt-android:2.43.2"
        const val HILT_KAPT = "com.google.dagger:hilt-android-compiler:2.43.2"
    }

    object Nav {
        const val NAV_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2"

        const val NAV_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:2.5.2"
        const val NAV_UI = "androidx.navigation:navigation-ui-ktx:2.5.2"
    }

    object Log {
        const val TIMBER = "com.jakewharton.timber:timber:5.0.1"
    }

    object Remote {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
        const val CONVERTER = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val HTTP = "com.squareup.okhttp3:logging-interceptor:4.10.0"
    }

    object Coroutine {
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
        const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3"
    }

    object Room {
        const val RUNTIME = "androidx.room:room-runtime:2.2.6"
        const val COMPILER = "androidx.room:room-compiler:2.2.6"
        const val ROOM_KTX = "androidx.room:room-ktx:2.2.6"
    }

    object LifeCycle {
        const val VM = "androidx.lifecycle:lifecycle-viewmodel:2.5.1"
        const val EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.0"
    }

    object Glide {
        const val GLIDE = "com.github.bumptech.glide:glide:4.13.2"
        const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:4.13.2"
    }

    object Paging {
        const val PAGING = "androidx.paging:paging-runtime:3.0.1"
    }

    object NaverMap {
        const val MAP = "com.naver.maps:map-sdk:3.16.0"
        const val LOCATION = "com.google.android.gms:play-services-location:16.0.0"
    }

    object TedPermission {
        const val NORMAL = "io.github.ParkSangGwon:tedpermission-normal:3.3.0"
        const val COROUTINE = "io.github.ParkSangGwon:tedpermission-coroutine:3.3.0"
    }

    object SlidingUpPanel {
        const val PANEL = "com.sothree.slidinguppanel:library:3.4.0"
    }
}