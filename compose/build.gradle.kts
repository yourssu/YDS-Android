import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

val versionProperties = Properties().apply {
    File(
        rootProject.rootDir,
        "version.properties"
    ).inputStream().use { load(it) }
}

android {
    namespace = "com.yourssu.design.system.compose"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("YDS-Compose") {
                from(components["release"])
                groupId = project.name
                artifactId = project.name
                version = versionProperties["versionName"].toString()
            }
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.03.00")
    implementation(composeBom)
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}