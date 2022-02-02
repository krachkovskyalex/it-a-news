package com.krachkovsky.it_anews.retrofit

import com.krachkovsky.it_anews.models.NewsEverything
import com.krachkovsky.it_anews.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getAllNews(
        @Query("q")
        q: String = "Belarus",
        @Query("page")
        page: Int = 1,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsEverything
}