package id.haweje.bukankompas.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getIndonesiaNewsList() : LiveData<List<LocalNewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIndonesiaNewsList(news: List<LocalNewsEntity>)

    @Update
    fun updateNews(news: LocalNewsEntity)

    @Query("SELECT * FROM news where bookmarked = 1")
    fun getBookmarkedNews() : LiveData<List<LocalNewsEntity>>


}