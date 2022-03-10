package com.example.buildsrc

object Versions {

    const val androidCoreVersion = "1.7.0"
    const val appcompatVersion = "1.4.1"
    const val materialVersion = "1.5.0"
    const val constraintLayoutVersion = "2.1.3"
    const val fragmentVersion = "1.4.1"

    const val jetpackNavigationVersion = "2.4.1"
    const val retrofitVersion = "2.9.0"
    const val okhttp3Version = "4.9.0"
    const val coilVersion = "1.4.0"
    const val coroutinesVersion = "1.6.0"
    const val lifecycleVersion = "2.4.1"
    const val paging3Version = "3.1.0"
    const val swipeRefreshVersion = "1.1.0"
    const val koinVersion = "3.1.5"
    const val roomVersion = "2.4.2"
    const val mapsUtilsVersion = "1.3.3"
    const val serviceMapsVersion = "18.0.2"
    const val serviceLocationVersion = "19.0.1"
}

object Deps {

    const val androidCore =
        "androidx.core:core-ktx:${Versions.androidCoreVersion}"
    const val appcompat =
        "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val material =
        "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val fragment =
        "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"

    // Navigation
    const val jetpackNavigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNavigationVersion}"
    const val jetpackNavigationUi =
        "androidx.navigation:navigation-ui-ktx:${Versions.jetpackNavigationVersion}"

    // Retrofit
    private const val retrofit =
        "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    private const val retrofitGson =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    // OKHTTP3
    const val okhttp3 =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}"

    // Coil
    const val coil =
        "io.coil-kt:coil:${Versions.coilVersion}"

    // Coroutines
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // Lifecycle
    const val lifecycle =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"

    // Paging3
    const val paging3 =
        "androidx.paging:paging-runtime-ktx:${Versions.paging3Version}"

    // SwipeRefresh
    const val swipeRefresh =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshVersion}"

    // Koin
    const val koin =
        "io.insert-koin:koin-android:${Versions.koinVersion}"

    // Room
    private const val room =
        "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler =
        "androidx.room:room-compiler:${Versions.roomVersion}"
    private const val roomKtx =
        "androidx.room:room-ktx:${Versions.roomVersion}"

    // Google Map
    const val mapsUtils =
        "com.google.maps.android:android-maps-utils:${Versions.mapsUtilsVersion}"
    const val servicesMaps =
        "com.google.android.gms:play-services-maps:${Versions.serviceMapsVersion}"
    const val servicesLocation =
        "com.google.android.gms:play-services-location:${Versions.serviceLocationVersion}"

    val retrofitLibraries = listOf(retrofit, retrofitGson)
    val roomLibraries = listOf(room, roomKtx)
}