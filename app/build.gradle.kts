plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose") //version "2.1.21" //don't add version gave error of different version at compilation

    id("com.google.protobuf") version "0.9.4"

}

android {
    namespace = "com.example.gettingstartedwithjetpackcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gettingstartedwithjetpackcompose"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    composeOptions {kotlinCompilerExtensionVersion = "1.6.1"}

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    //sourceSets {getByName("main") {proto.srcDir("src/main/proto")}}

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation("androidx.compose.ui:ui")  //implementation(libs.androidx.ui)
    implementation("androidx.compose.ui:ui-graphics")  //implementation(libs.androidx.ui.graphics)
    implementation("androidx.compose.ui:ui-tooling-preview")  //implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.navigation.runtime.android)
    implementation("androidx.compose.ui:ui-text")  //implementation(libs.androidx.ui.text.android)
    //implementation(libs.androidx.navigation.compose.jvmstubs)
    // implementation(libs.androidx.material3.jvmstubs)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Room
    implementation ("androidx.room:room-runtime:2.7.2")
    kapt("androidx.room:room-compiler:2.7.2")
    //annotationProcessor ("androidx.room:room-compiler:2.7.2")

//    kapt("androidx.room:room-compiler:2.7.2")
//    annotationProcessor ("androidx.room:room-compiler:2.7.2")

    //Hilt ViewModel
    implementation("com.google.dagger:hilt-android:2.56")
    kapt("com.google.dagger:hilt-compiler:2.56")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //kapt("com.google.dagger:hilt-compiler:2.56")
    //implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    //DataStore
    implementation("androidx.datastore:datastore:1.1.7") //main datastore
    implementation("androidx.datastore:datastore-core:1.1.7")
    implementation("com.google.protobuf:protobuf-java:3.25.3")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    //for animation in splash screen
    implementation ("com.airbnb.android:lottie-compose:6.6.7")

    //retrofit for APIs
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation ("androidx.compose.material:material:1.8.3")
    implementation ("androidx.compose.runtime:runtime-livedata:1.8.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.9.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

    implementation("androidx.datastore:datastore-preferences:1.1.7")

    implementation("androidx.compose.material:material:1.8.3") // for SwipeToDismiss
    implementation("com.google.accompanist:accompanist-swiperefresh:0.33.2-alpha") // for SwipeRefresh

    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    //Moshi converts between JSON and Kotlin objects
    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")


}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:3.25.5"}
    generateProtoTasks {
        all().forEach { task -> task.builtins {
            maybeCreate("java")
                //.apply { option("lite")}
        }
        }
    }
}