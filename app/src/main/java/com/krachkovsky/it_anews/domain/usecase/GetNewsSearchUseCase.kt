package com.krachkovsky.it_anews.domain.usecase

import com.krachkovsky.it_anews.data.mappers.toDomainModel
import com.krachkovsky.it_anews.domain.models.NewsEverything
import com.krachkovsky.it_anews.domain.repository.NewsRepository

class GetNewsSearchUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int, q: String): NewsEverything {
        return newsRepository.getSearchNews(page, pageSize, q).toDomainModel()
    }
}