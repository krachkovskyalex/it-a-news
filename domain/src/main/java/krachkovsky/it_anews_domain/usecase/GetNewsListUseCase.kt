package krachkovsky.it_anews_domain.usecase

import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.repository.NewsRepository

class GetNewsListUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int, newsCategory: String): NewsEverything {
        return newsRepository.getAllNews(page, pageSize, newsCategory)
    }
}