import java.net.URI

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.Plugins.android)
        classpath(Dependencies.Plugins.kotlin)
        classpath(Dependencies.gms)
        classpath(Dependencies.Material.navSafeArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = URI.create("https://jitpack.io") }
        maven("https://dl.bintray.com/guardian/android")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}