package com.krachkovsky.it_anews.data.api

import com.krachkovsky.it_anews.data.models.NewsApiEverything
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category")
        category: String = "general",
        @Query("country")
        country: String = "ru",
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int
    ): NewsApiEverything
}