package com.krachkovsky.it_anews.data.models

import com.google.gson.annotations.SerializedName

data class NewsApiEverything(
    @SerializedName("articles")
    val newsApiArticles: List<NewsApiArticle>,
    val status: String,
    val totalResults: Int
)