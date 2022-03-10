package com.krachkovsky.it_anews.domain.models

data class NewsEverything(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)