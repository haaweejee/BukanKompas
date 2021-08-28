package id.haweje.bukankompas.core.data.source.local

import androidx.lifecycle.LiveData
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity
import id.haweje.bukankompas.core.data.source.local.room.NewsDao

class LocalDataSource private constructor(private val mNewsDao: NewsDao){

    companion object{
        private var INSTANCE : LocalDataSource? = null

        fun getInstance(newsDao: NewsDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(newsDao)
    }

    fun getIndonesiaNewsList() : LiveData<List<LocalNewsEntity>> = mNewsDao.getIndonesiaNewsList()

    fun getBookmarkedNewsList() : LiveData<List<LocalNewsEntity>> = mNewsDao.getBookmarkedNews()

    fun insertIndonesiaNewsList(news: List<LocalNewsEntity>) = mNewsDao.insertIndonesiaNewsList(news)

    fun setBookmarkNews(news: LocalNewsEntity, bookmarked : Boolean){
        news.bookmarked = bookmarked
        mNewsDao.updateNews(news)
    }

}