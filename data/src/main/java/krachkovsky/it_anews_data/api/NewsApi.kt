package krachkovsky.it_anews_data.api

import krachkovsky.it_anews_data.models.NewsEverythingApi
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
    ): NewsEverythingApi

    @GET("everything")
    suspend fun getEverything(
        @Query("q")
        q: String,
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int
    ): NewsEverythingApi
}