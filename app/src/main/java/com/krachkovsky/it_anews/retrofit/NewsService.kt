package com.krachkovsky.it_anews.retrofit

import com.krachkovsky.it_anews.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NewsService {
    private val retrofit by lazy(LazyThreadSafetyMode.NONE) { provideRetrofit() }
    val newsApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create<NewsApi>()
    }

    private fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}