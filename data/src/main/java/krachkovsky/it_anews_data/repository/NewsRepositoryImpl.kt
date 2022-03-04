package krachkovsky.it_anews_data.repository

import krachkovsky.it_anews_data.api.NewsApi
import krachkovsky.it_anews_data.mappers.toDomainModel
import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.repository.NewsRepository

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {

    override suspend fun getAllNews(
        page: Int,
        pageSize: Int,
        newsCategory: String
    ): NewsEverything = newsApi
        .getTopHeadlines(page = page, pageSize = pageSize, category = newsCategory)
        .toDomainModel()

    override suspend fun getSearchNews(
        page: Int,
        pageSize: Int,
        q: String
    ): NewsEverything = newsApi
        .getEverything(page = page, pageSize = pageSize, q = q)
        .toDomainModel()
}