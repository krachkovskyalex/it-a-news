package krachkovsky.it_anews_data.mappers

import krachkovsky.it_anews_data.database.NewsArticleRoom
import krachkovsky.it_anews_data.database.NewsSourceRoom
import krachkovsky.it_anews_data.models.NewsArticleApi
import krachkovsky.it_anews_data.models.NewsEverythingApi
import krachkovsky.it_anews_data.models.NewsSourceApi
import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.models.NewsEverything
import krachkovsky.it_anews_domain.models.Source

fun NewsSourceApi.toDomainModel(): Source {
    return Source(
        id = id,
        name = name
    )
}

fun NewsArticleApi.toDomainModel(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = newsSourceApi.toDomainModel(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun NewsEverythingApi.toDomainModel(): NewsEverything {
    return NewsEverything(
        articles = newsArticlesApi.map { it.toDomainModel() },
        status = status,
        totalResults = totalResults
    )
}

fun NewsSourceRoom.toDomainModel(): Source {
    return Source(
        id = id,
        name = name
    )
}

fun NewsArticleRoom.toDomainModel(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = newsSource.toDomainModel(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Article.toDaoModel(): NewsArticleRoom {
    return NewsArticleRoom(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        newsSource = source.toDomainModel(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Source.toDomainModel(): NewsSourceRoom {
    return NewsSourceRoom(
        id = id,
        name = name
    )
}