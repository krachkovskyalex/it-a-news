package krachkovsky.it_anews_domain.usecase

import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.repository.NewsRepository

class GetNewsSearchUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int, q: String): NewsEverything {
        return newsRepository.getSearchNews(page, pageSize, q)
    }
}