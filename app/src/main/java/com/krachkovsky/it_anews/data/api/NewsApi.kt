package com.krachkovsky.it_anews.data.api

import com.krachkovsky.it_anews.data.models.NewsApiEverything
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getAllNews(
        @Query("q")
        q: String = "Russia",
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int
    ): NewsApiEverything
}