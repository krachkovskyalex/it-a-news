package com.krachkovsky.it_anews.models

data class NewsEverything(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)