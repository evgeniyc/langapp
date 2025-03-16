plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.android) apply false
}