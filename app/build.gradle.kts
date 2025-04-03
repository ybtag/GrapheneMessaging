android {
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    namespace = "com.android.messaging"

    defaultConfig {
        versionCode = 10001040 + 1
        versionName = "1.0.001"
        minSdk = 24
        targetSdk = 24
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "Messaging d")
        }
    }

    sourceSets.getByName("main") {
        assets.srcDir("../assets")
        manifest.srcFile("../AndroidManifest.xml")
        java.srcDirs("../src")
        res.srcDir("../res")
    }

    packaging {
        resources {
            pickFirsts += "*/mms_config.xml" // Broader pattern
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.palette:palette:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.guava:guava:33.4.0-android")
    implementation(project(":lib:platform_external_libphonenumber"))
    implementation(project(":lib:platform_frameworks_ex:common"))
    implementation(project(":lib:platform_frameworks_ex:framesequence"))
    implementation(project(":lib:platform_frameworks_opt_chips"))
    implementation(project(":lib:platform_frameworks_opt_colorpicker"))
    implementation(project(":lib:platform_frameworks_opt_photoviewer"))
    implementation(project(":lib:platform_frameworks_opt_vcard"))
}
