import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "2.0.21"
    id("com.android.application") version "8.10.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "composeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.compose.material3:material3:1.6.10")
                implementation("org.jetbrains.compose.foundation:foundation:1.6.10")
                implementation("org.jetbrains.compose.runtime:runtime:1.6.10")
                implementation("org.jetbrains.compose.ui:ui:1.6.10")
                implementation("org.jetbrains.compose.ui:ui-graphics:1.6.10")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
                implementation("com.squareup.okio:okio:3.9.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.compose.ui:ui-test-junit4:1.6.10")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.15.0")
                implementation("androidx.activity:activity-compose:1.10.0")
                implementation("androidx.compose.ui:ui-tooling-preview:1.7.6")
                implementation(platform("androidx.compose:compose-bom:2024.12.01"))
                implementation("androidx.compose.ui:ui")
                implementation("androidx.compose.ui:ui-graphics")
                implementation("androidx.compose.material3:material3")
                implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
                implementation("androidx.compose.ui:ui-tooling:1.7.6")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("androidx.compose.ui:ui-test-junit4:1.7.6")
                implementation("androidx.compose.ui:ui-test-manifest:1.7.6")
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation("androidx.compose.ui:ui-test-junit4:1.7.6")
                implementation("androidx.compose.ui:ui-test-manifest:1.7.6")
            }
        }
        val iosMain by creating {
            dependencies {
                implementation("org.jetbrains.compose.material3:material3:1.6.10")
                implementation("org.jetbrains.compose.foundation:foundation:1.6.10")
                implementation("org.jetbrains.compose.runtime:runtime:1.6.10")
                implementation("org.jetbrains.compose.ui:ui:1.6.10")
                implementation("org.jetbrains.compose.ui:ui-graphics:1.6.10")
            }
        }
        val iosSimulatorArm64Main by getting {
        }
        val iosArm64Main by getting {
        }
        val iosX64Main by getting {
        }
    }

    // JVM target for Android
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

android {
    namespace = "tz.emergency.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "tz.emergency.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "DEBUG_TEST_PHONE_NUMBER", "\"\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    signingConfigs {
        create("release") {
            val keystorePath = project.findProperty("KEYSTORE_PATH")?.toString()
            val keystorePassword = project.findProperty("KEYSTORE_PASSWORD")?.toString()
            val keyAliasProp = project.findProperty("KEY_ALIAS")?.toString()
            val keyPasswordProp = project.findProperty("KEY_PASSWORD")?.toString()
            if (keystorePath != null && keystorePassword != null && keyAliasProp != null && keyPasswordProp != null) {
                storeFile = file(keystorePath)
                storePassword = keystorePassword
                keyAlias = keyAliasProp
                keyPassword = keyPasswordProp
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            val debugPhoneNumber = project.findProperty("DEBUG_TEST_PHONE_NUMBER")?.toString() ?: ""
            buildConfigField(
                "String",
                "DEBUG_TEST_PHONE_NUMBER",
                if (debugPhoneNumber.isNotBlank()) "\"$debugPhoneNumber\"" else "\"\""
            )
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            val keystorePath = project.findProperty("KEYSTORE_PATH")?.toString()
            val keystorePassword = project.findProperty("KEYSTORE_PASSWORD")?.toString()
            val keyAliasProp = project.findProperty("KEY_ALIAS")?.toString()
            val keyPasswordProp = project.findProperty("KEY_PASSWORD")?.toString()
            if (keystorePath != null && keystorePassword != null && keyAliasProp != null && keyPasswordProp != null) {
                signingConfig = signingConfigs.getByName("release")
            }
            buildConfigField(
                "String",
                "DEBUG_TEST_PHONE_NUMBER",
                "\"\""
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }
}

dependencies {
    // Common dependencies are in sourceSets
}