package krachkovsky.it_anews_domain.usecase

import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.repository.NewsDBRepository

class GetNewsSavedUseCase(private val newsDBRepository: NewsDBRepository) {

    suspend operator fun invoke(): List<Article> {
        return newsDBRepository.getSavedNews()
    }
}