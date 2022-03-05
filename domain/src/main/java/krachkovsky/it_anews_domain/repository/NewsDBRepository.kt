package krachkovsky.it_anews_domain.repository

import krachkovsky.it_anews_domain.models.Article

interface NewsDBRepository {

    suspend fun getSavedNews(): List<Article>

    suspend fun insertArticle(article: Article)

    suspend fun deleteArticle(article: Article)
}