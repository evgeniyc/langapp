plugins {
   id("com.google.devtools.ksp") version "2.1.10-1.0.29" apply false
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.kotlin.android) apply false
   alias(libs.plugins.hilt.android) apply false
   alias(libs.plugins.compose.compiler) apply false
   //id("com.google.dagger.hilt.android") version "2.50" apply false

}