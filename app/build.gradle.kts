plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(Versions.androidCompileSdk)

    defaultConfig {
        minSdkVersion(Versions.androidMinSdk)
        targetSdkVersion(Versions.androidTargetSdk)
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        applicationId = AndroidIds.androidApplicationId
        versionCode = Versions.androidVersionCode
        versionName = Versions.androidVersionName

        testInstrumentationRunner = AndroidIds.testInstrumentationRunner
        testApplicationId = AndroidIds.testApplicationId
    }

    signingConfigs {
        create("release") {
            storeFile =
                File(rootProject.projectDir.absolutePath + "/sign/key")
            storePassword = "dksj2893d32dsa"
            keyAlias = "avsl"
            keyPassword = "jfnd2cv948nfs"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".dev"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    // Jdk
    implementation(Dependencies.Stdlib.jdk)

    // Support Libraries
    implementation(Dependencies.Material.lifecycleComponents)
    implementation(Dependencies.Material.materialComponents)
    implementation(Dependencies.Material.prefs)
    implementation(Dependencies.Ktx.core)
    implementation(Dependencies.multidex)

    implementation(Dependencies.Material.appcompat)
    implementation(Dependencies.Ktx.activity)

    implementation(Dependencies.Material.navFragment)
    implementation(Dependencies.Material.navUi)

    // Play Services
    api(Dependencies.PlayServices.mapUtils)
    api(Dependencies.PlayServices.mapUtilsKtx)
    api(Dependencies.PlayServices.maps)

    // Dagger
    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)

    // Rx
    implementation(Dependencies.Rx.rxAndroid)
    implementation(Dependencies.Rx.rxJava)

    // Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.converterMoshi)
    implementation(Dependencies.Retrofit.adapterRxJava)

    // Moshi
    implementation(Dependencies.Moshi.moshi)
    implementation(Dependencies.Moshi.moshiKotlin)
    kapt(Dependencies.Moshi.moshiCodegen)

    // OkHttp
    implementation(Dependencies.okhttp)

    // Timber
    implementation(Dependencies.timber)

    // Edge to edge
    implementation(Dependencies.edgeToEdge)

    // Groupie
    api(Dependencies.groupie)
    api(Dependencies.groupieAndroidExt)
}

apply(plugin = "androidx.navigation.safeargs.kotlin")