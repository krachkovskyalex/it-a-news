package krachkovsky.it_anews_data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "article"
)
data class NewsArticleRoom(
    val id: Long = 0L,
    val author: String?,
    val content: String?,
    val description: String?,
    @ColumnInfo(name = "published_at")
    val publishedAt: String?,
    @ColumnInfo(name = "news_source")
    val newsSource: NewsSourceRoom,
    val title: String?,
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String?
)