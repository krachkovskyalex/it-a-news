package com.krachkovsky.it_anews.data.mappers

import com.krachkovsky.it_anews.data.models.NewsApiArticle
import com.krachkovsky.it_anews.data.models.NewsApiEverything
import com.krachkovsky.it_anews.data.models.NewsApiSource
import com.krachkovsky.it_anews.domain.models.Article
import com.krachkovsky.it_anews.domain.models.NewsEverything
import com.krachkovsky.it_anews.domain.models.Source

fun NewsApiSource.toDomainModel(): Source {
    return Source(
        id = id,
        name = name
    )
}

fun NewsApiArticle.toDomainModel(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = newsApiSource.toDomainModel(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun NewsApiEverything.toDomainModel(): NewsEverything {
    return NewsEverything(
        articles = newsApiArticles.map { it.toDomainModel() },
        status = status,
        totalResults = totalResults
    )
}