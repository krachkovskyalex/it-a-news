package krachkovsky.it_anews_domain.usecase

import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.repository.NewsApiRepository

class GetNewsListUseCase(private val newsApiRepository: NewsApiRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int, newsCategory: String): NewsEverything {
        return newsApiRepository.getAllNews(page, pageSize, newsCategory)
    }
}