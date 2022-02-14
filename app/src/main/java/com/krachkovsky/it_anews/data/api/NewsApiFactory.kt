package com.krachkovsky.it_anews.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsApiFactory {

    private val retrofit by lazy(LazyThreadSafetyMode.NONE) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(name = API_KEY_NAME, value = API_KEY)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideApi() = retrofit.create<NewsApi>()

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        private const val API_KEY_NAME = "X-Api-Key"
        private const val API_KEY = "08e6c887e888450baeec6d57ba241fbe"
    }
}

