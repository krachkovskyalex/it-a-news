package com.krachkovsky.it_anews.data.repository

import com.krachkovsky.it_anews.data.api.NewsApi
import com.krachkovsky.it_anews.data.models.NewsApiEverything
import com.krachkovsky.it_anews.domain.repository.NewsRepository

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {

    override suspend fun getAllNews(
        page: Int,
        pageSize: Int,
        newsCategory: String
    ): NewsApiEverything =
        newsApi.getTopHeadlines(page = page, pageSize = pageSize, category = newsCategory)
}