package id.haweje.bukankompas.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.haweje.bukankompas.core.data.source.local.entity.BookmarkEntity
import id.haweje.bukankompas.core.data.source.local.entity.HeadlinesEntity
import id.haweje.bukankompas.core.data.source.local.entity.TechEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM headlines")
    fun getIndonesiaNewsList() : LiveData<List<HeadlinesEntity>>

    @Query("SELECT * FROM tech")
    fun getIndonesiaTechNewsList() : LiveData<List<TechEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIndonesiaNewsList(news: List<HeadlinesEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIndonesiaTechNewsList(news: List<TechEntity>)

    @Update
    fun updateNews(news: BookmarkEntity)

    @Query("SELECT * FROM bookmark where bookmarked = 1")
    fun getBookmarkedNews() : LiveData<List<BookmarkEntity>>


}