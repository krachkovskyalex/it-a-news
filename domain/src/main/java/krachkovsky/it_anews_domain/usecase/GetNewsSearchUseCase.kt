package krachkovsky.it_anews_domain.usecase

import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.repository.NewsApiRepository

class GetNewsSearchUseCase(private val newsApiRepository: NewsApiRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int, q: String): NewsEverything {
        return newsApiRepository.getSearchNews(page, pageSize, q)
    }
}