package krachkovsky.it_anews_data.mappers

import krachkovsky.it_anews_data.models.NewsApiArticle
import krachkovsky.it_anews_data.models.NewsApiEverything
import krachkovsky.it_anews_data.models.NewsApiSource
import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.models.Source

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