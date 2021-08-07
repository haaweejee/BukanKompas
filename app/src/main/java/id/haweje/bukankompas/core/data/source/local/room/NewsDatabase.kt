package id.haweje.bukankompas.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.haweje.bukankompas.core.data.source.local.entity.BookmarkEntity
import id.haweje.bukankompas.core.data.source.local.entity.HeadlinesEntity
import id.haweje.bukankompas.core.data.source.local.entity.TechEntity

@Database(entities = [
    HeadlinesEntity::class,
    TechEntity::class,
    BookmarkEntity::class
                     ], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao

    companion object{
        @Volatile
        private var INSTANCE : NewsDatabase? = null

        fun getInstance(context: Context) : NewsDatabase =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "NewsDb"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}