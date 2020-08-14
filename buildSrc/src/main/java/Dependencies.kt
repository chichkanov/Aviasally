object AndroidIds {
    const val androidApplicationId = "com.chichkanov.aviasally"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val testApplicationId = "com.chichkanov.aviasally.test"
}

object Versions {
    // Android
    const val androidMinSdk = 21
    const val androidTargetSdk = 29
    const val androidCompileSdk = 29
    const val androidVersionCode = 1
    const val androidVersionName = "3.0.0"

    // Google
    const val materialComponents = "1.0.0"
    const val preferences = "1.1.0"
    const val androidx = "1.1.0"
    const val lifecycleComponents = "2.2.0"
    const val ktx = "1.1.0"
    const val ktxActivity = "1.1.0"
    const val gms = "4.3.3"
    const val multidex = "2.0.1"
    const val navigation = "2.3.0"

    // Libraries
    const val inject = "1"
    const val dagger = "2.27"
    const val rxJava = "2.2.19"
    const val rxAndroid = "2.1.1"
    const val moshi = "1.9.2"
    const val retrofit = "2.7.2"
    const val okhttp = "4.3.1"
    const val timber = "4.7.1"
    const val playServices = "17.0.0"

    // Plugins
    const val kotlin = "1.3.71"
    const val android = "3.6.0"

    // Edge to Edge
    const val edgeToEdge = "1.0-rc1"

    // Groupie
    const val groupie = "2.7.2"

    //Map utils
    const val mapUtils = "2.0.3"
    const val mapUtilsKtx = "1.7.0"
}

object Dependencies {
    const val okhttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val gms = "com.google.gms:google-services:${Versions.gms}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupieAndroidExt = "com.xwray:groupie-kotlin-android-extensions:${Versions.groupie}"

    const val edgeToEdge = "de.halfbit:edge-to-edge:${Versions.edgeToEdge}"

    object Material {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.androidx}"
        const val materialComponents =
            "com.google.android.material:material:${Versions.materialComponents}"
        const val lifecycleComponents =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleComponents}"
        const val prefs = "androidx.preference:preference:${Versions.preferences}"
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val navSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    }

    object PlayServices {
        const val maps = "com.google.android.gms:play-services-maps:${Versions.playServices}"
        const val mapUtils = "com.google.maps.android:android-maps-utils:${Versions.mapUtils}"
        const val mapUtilsKtx = "com.google.maps.android:maps-utils-ktx:${Versions.mapUtilsKtx}"
    }

    object Ktx {
        const val activity = "androidx.activity:activity-ktx:${Versions.ktxActivity}"
        const val core = "androidx.core:core-ktx:${Versions.ktx}"
    }

    object Plugins {
        const val android = "com.android.tools.build:gradle:${Versions.android}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Stdlib {
        const val jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    object Moshi {
        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val adapterRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    object Dagger {
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val android = "com.google.dagger:dagger-android:${Versions.dagger}"
    }

    object Rx {
        const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    }
}