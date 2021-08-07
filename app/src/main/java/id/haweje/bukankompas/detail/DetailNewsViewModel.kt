package id.haweje.bukankompas.detail

import androidx.lifecycle.ViewModel
import id.haweje.bukankompas.core.data.NewsRepository
import id.haweje.bukankompas.core.data.source.local.entity.BookmarkEntity

class DetailNewsViewModel(private val newsRepository: NewsRepository) : ViewModel(){


    fun setBookmark(news: BookmarkEntity, bookmark: Boolean)  {
        newsRepository.setBookmarked(news, bookmark)
    }
}