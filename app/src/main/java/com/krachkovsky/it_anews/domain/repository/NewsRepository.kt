package com.krachkovsky.it_anews.domain.repository

import com.krachkovsky.it_anews.data.models.NewsApiEverything

interface NewsRepository {

    suspend fun getAllNews(page: Int, pageSize: Int): NewsApiEverything
}