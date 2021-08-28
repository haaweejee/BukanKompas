package id.haweje.bukankompas.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.haweje.bukankompas.core.data.NewsRepository
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity

class BookmarkViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getBookmarkedNews() : LiveData<List<LocalNewsEntity>> = newsRepository.getBookmarkedNews()
}