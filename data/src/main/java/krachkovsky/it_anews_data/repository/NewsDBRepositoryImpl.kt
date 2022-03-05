package krachkovsky.it_anews_data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import krachkovsky.it_anews_data.database.NewsDB
import krachkovsky.it_anews_data.mappers.toDaoModel
import krachkovsky.it_anews_data.mappers.toDomainModel
import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.repository.NewsDBRepository

class NewsDBRepositoryImpl(private val db: NewsDB) : NewsDBRepository {

    override suspend fun getSavedNews(): List<Article> =
        coroutineScope {
            withContext(Dispatchers.IO) {
                db.catsDao().getAll().map { it.toDomainModel() }
            }
        }

    override suspend fun insertArticle(article: Article) {
        coroutineScope {
            withContext(Dispatchers.IO) {
                db.catsDao().insertArticle(article.toDaoModel())
            }
        }
    }

    override suspend fun deleteArticle(article: Article) {
        coroutineScope {
            withContext(Dispatchers.IO) {
                db.catsDao().deleteArticle(article.toDaoModel())
            }
        }
    }
}