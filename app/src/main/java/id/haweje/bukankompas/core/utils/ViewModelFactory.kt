package id.haweje.bukankompas.core.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.haweje.bukankompas.account.BookmarkViewModel
import id.haweje.bukankompas.core.data.NewsRepository
import id.haweje.bukankompas.core.di.Injection
import id.haweje.bukankompas.detail.DetailNewsViewModel
import id.haweje.bukankompas.home.NewsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val newsRepository: NewsRepository)
    : ViewModelProvider.NewInstanceFactory() {

        companion object{
            @Volatile
            private var instance : ViewModelFactory? = null

            fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this){
                    instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                        instance = this
                    }
                }
        }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(NewsViewModel::class.java) ->{
                NewsViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(DetailNewsViewModel::class.java) ->{
                DetailNewsViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                BookmarkViewModel(newsRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown Viewmodel class: " + modelClass.name)
        }
    }
}