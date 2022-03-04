package krachkovsky.it_anews_domain.models

data class NewsEverything(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)