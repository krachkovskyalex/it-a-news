package com.krachkovsky.it_anews.domain.usecase

import com.krachkovsky.it_anews.data.mappers.toDomainModel
import com.krachkovsky.it_anews.domain.models.NewsEverything
import com.krachkovsky.it_anews.domain.repository.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int): NewsEverything {
        return newsRepository.getAllNews(page, pageSize).toDomainModel()
    }
}