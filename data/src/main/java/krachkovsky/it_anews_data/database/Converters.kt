package krachkovsky.it_anews_data.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromSource(source: NewsSourceRoom): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): NewsSourceRoom {
        return NewsSourceRoom(name, name)
    }
}