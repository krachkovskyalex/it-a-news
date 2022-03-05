package krachkovsky.it_anews_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [NewsArticleRoom::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NewsDB : RoomDatabase() {

    companion object {

        private var db: NewsDB? = null
        private val LOCK = Any()

        fun getInstance(context: Context): NewsDB {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    NewsDB::class.java,
                    "news_database"
                ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun catsDao(): NewsDao
}