plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false // KSP Plugin
}

buildscript {
    dependencies {
        // Remove the old Hilt plugin dependency from here
        // classpath("com.google.dagger:hilt-android-gradle-plugin:2.44") --> Remove this
    }
}
