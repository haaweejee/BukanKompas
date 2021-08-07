package id.haweje.bukankompas.core.data

import androidx.lifecycle.LiveData
import id.haweje.bukankompas.core.data.source.local.entity.BookmarkEntity
import id.haweje.bukankompas.core.data.source.local.entity.HeadlinesEntity
import id.haweje.bukankompas.core.data.source.local.entity.TechEntity
import id.haweje.bukankompas.core.vo.Resource

interface NewsDataSource {

    fun getIndonesiaNews() : LiveData<Resource<List<HeadlinesEntity>>>

    fun getIndonesiaTechNews() : LiveData<Resource<List<TechEntity>>>

    fun getBookmarkedNews(): LiveData<List<BookmarkEntity>>

    fun insertIndonesiaNewsList(news : List<HeadlinesEntity>)

    fun insertIndonesiaNewsTechList(news: List<TechEntity>)

    fun setBookmarked(news: BookmarkEntity, bookmarked: Boolean)
}