package id.haweje.bukankompas.core.data.source.local

import androidx.lifecycle.LiveData
import id.haweje.bukankompas.core.data.source.local.entity.BookmarkEntity
import id.haweje.bukankompas.core.data.source.local.entity.HeadlinesEntity
import id.haweje.bukankompas.core.data.source.local.entity.TechEntity
import id.haweje.bukankompas.core.data.source.local.room.NewsDao

class LocalDataSource private constructor(private val mNewsDao: NewsDao){

    companion object{
        private var INSTANCE : LocalDataSource? = null

        fun getInstance(newsDao: NewsDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(newsDao)
    }

    fun getIndonesiaNewsList() : LiveData<List<HeadlinesEntity>> = mNewsDao.getIndonesiaNewsList()

    fun getIndonesiaTechNewsList() : LiveData<List<TechEntity>> = mNewsDao.getIndonesiaTechNewsList()

    fun getBookmarkedNewsList() : LiveData<List<BookmarkEntity>> = mNewsDao.getBookmarkedNews()

    fun insertIndonesiaNewsList(news: List<HeadlinesEntity>) = mNewsDao.insertIndonesiaNewsList(news)

    fun insertIndonesiaTechNewsList(news: List<TechEntity>) = mNewsDao.insertIndonesiaTechNewsList(news)

    fun setBookmarkNews(news: BookmarkEntity, bookmarked : Boolean){
        news.bookmarked = bookmarked
        mNewsDao.updateNews(news)
    }

}