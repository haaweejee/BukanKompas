package id.haweje.bukankompas.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.haweje.bukankompas.core.data.NewsRepository
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity
import id.haweje.bukankompas.core.vo.Resource

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getIndonesiaNews(): LiveData<Resource<List<LocalNewsEntity>>> = newsRepository.getIndonesiaNews()
}