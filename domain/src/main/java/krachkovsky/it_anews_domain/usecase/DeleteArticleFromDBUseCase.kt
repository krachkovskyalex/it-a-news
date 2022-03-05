package krachkovsky.it_anews_domain.usecase

import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.repository.NewsDBRepository

class DeleteArticleFromDBUseCase(private val newsDBRepository: NewsDBRepository) {

    suspend operator fun invoke(article: Article) {
        return newsDBRepository.deleteArticle(article)
    }
}