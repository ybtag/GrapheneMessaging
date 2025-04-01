import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

android {
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    namespace = "com.android.messaging"

    defaultConfig {
        versionCode = 20000000 + 8
        versionName = "8"
        minSdk = 27
        targetSdk = 35

        ndk {
            abiFilters.clear()
            abiFilters.addAll(listOf("arm64-v8a", "x86_64"))
        }
    }

    externalNativeBuild {
        cmake {
            path = file("../CMakeLists.txt")
            version = "3.22.1"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    sourceSets.getByName("main") {
        assets.srcDir("../assets")
        manifest.srcFile("../AndroidManifest.xml")
        java.srcDirs("../src")
        res.srcDir("../res")
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val useKeystoreProperties = keystorePropertiesFile.canRead()
    val keystoreProperties = Properties()
    if (useKeystoreProperties) {
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))
    }

    if (useKeystoreProperties) {
        signingConfigs {
            create("release") {
                storeFile = rootProject.file(keystoreProperties["storeFile"]!!)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                enableV4Signing = true
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"),
                    "../proguard.flags", "../proguard-release.flags")
            if (useKeystoreProperties) {
                signingConfig = signingConfigs.getByName("release")
            }
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "Messaging d")
        }
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.palette:palette:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.guava:guava:33.4.0-android")
    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.52")

    implementation(project(":lib:platform_frameworks_opt_chips"))
    implementation(project(":lib:platform_frameworks_opt_photoviewer"))
    implementation(project(":lib:platform_frameworks_opt_vcard"))
}
