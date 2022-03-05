package krachkovsky.it_anews_data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NewsDao {
    @Query("SELECT * FROM article")
    fun getAll(): List<NewsArticleRoom>

    @Insert(onConflict = REPLACE)
    fun insertArticle(article: NewsArticleRoom)

    @Delete
    fun deleteArticle(article: NewsArticleRoom)
}