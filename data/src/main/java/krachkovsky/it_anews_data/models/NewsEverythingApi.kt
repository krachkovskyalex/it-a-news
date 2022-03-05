package krachkovsky.it_anews_data.models

import com.google.gson.annotations.SerializedName

data class NewsEverythingApi(
    @SerializedName("articles")
    val newsArticlesApi: List<NewsArticleApi>,
    val status: String,
    val totalResults: Int
)