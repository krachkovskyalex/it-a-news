package com.krachkovsky.it_anews.provider

import com.krachkovsky.it_anews.retrofit.NewsApi
import com.krachkovsky.it_anews.retrofit.NewsApiFactory

object ServiceProvider {

    private val apiFactory by lazy { NewsApiFactory() }

    fun provideNewsApi(): NewsApi = apiFactory.provideApi()
}