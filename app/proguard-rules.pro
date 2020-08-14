# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepclassmembers enum * { *; }
-dontwarn okio.**

# Костыль для navigation compoennt https://stackoverflow.com/questions/50378810/proguard-causing-runtime-exception-with-android-navigation-component/50378828#50378828
-keep class * extends androidx.fragment.app.Fragment{}
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable

# Moshi
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class com.chichkanov.aviasally.core.domain.City {
  <init>(...);
  <fields>;
}
-keepclassmembers class com.chichkanov.aviasally.core.domain.Coordinates {
  <init>(...);
  <fields>;
}