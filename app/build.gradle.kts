import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

var keystoreProperties = Properties()
var keystorePropertiesFile = rootProject.file("../keystore.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "jp.co.integrityworks.mysiminfo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "jp.co.integrityworks.mysiminfo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 13
        versionName = "1.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".deb"
            isDebuggable = true
            isMinifyEnabled = false
            manifestPlaceholders["admob_app_id"] = project.properties["admobAppIdSample"] as String
            buildConfigField(
                "String",
                "admob_app_id",
                '"' + "${project.properties["admobAppIdSample"] ?: ""}" + '"'
            )
            resValue(
                "string",
                "ad_unit_id",
                project.properties["admobBannerSample"] as String
            )
        }
        release {
            isMinifyEnabled = true
            manifestPlaceholders["admob_app_id"] =
                project.properties["admobAppIdStoragePath"] as String
            buildConfigField(
                "String",
                "admob_app_id",
                '"' + "${project.properties["admobAppIdStoragePath"] ?: ""}" + '"'
            )
            resValue(
                "string",
                "ad_unit_id",
                project.properties["admobBannerStoragePath"] as String
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.compose.material3)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.graphics)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.espresso.core)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.permissions)
    implementation(libs.play.services.ads)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}