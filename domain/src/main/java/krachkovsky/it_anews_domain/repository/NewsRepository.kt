package krachkovsky.it_anews_domain.repository

import krachkovsky.it_anews_domain.models.NewsEverything

interface NewsRepository {

    suspend fun getAllNews(page: Int, pageSize: Int, newsCategory: String): NewsEverything

    suspend fun getSearchNews(page: Int, pageSize: Int, q: String): NewsEverything
}