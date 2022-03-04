package krachkovsky.it_anews_data.models

import com.google.gson.annotations.SerializedName

data class NewsApiArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @SerializedName("source")
    val newsApiSource: NewsApiSource,
    val title: String?,
    val url: String,
    val urlToImage: String?
)