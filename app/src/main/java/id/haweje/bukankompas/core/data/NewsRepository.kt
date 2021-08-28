package id.haweje.bukankompas.core.data

import androidx.lifecycle.LiveData
import id.haweje.bukankompas.core.data.source.local.LocalDataSource
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity
import id.haweje.bukankompas.core.data.source.remote.ApiResponse
import id.haweje.bukankompas.core.data.source.remote.RemoteDataSource
import id.haweje.bukankompas.core.data.source.remote.response.Article
import id.haweje.bukankompas.core.utils.AppExecutors
import id.haweje.bukankompas.core.vo.Resource

class NewsRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors) :
    NewsDataSource {
    companion object{
        private var instance : NewsRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors): NewsRepository =
            instance ?: synchronized(this){
                instance ?: NewsRepository(
                    remoteDataSource,
                    localDataSource,
                    appExecutors
                ).apply { instance = this }
            }
    }

    override fun getIndonesiaNews(): LiveData<Resource<List<LocalNewsEntity>>> {
        return object : NetworkBoundResource<List<LocalNewsEntity>, List<Article>>(appExecutors){
            override fun loadFromDb(): LiveData<List<LocalNewsEntity>> = localDataSource.getIndonesiaNewsList()

            override fun shouldFetch(resultType: List<LocalNewsEntity>?): Boolean =
                resultType == null || resultType.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Article>>> =
                remoteDataSource.getIndonesiaNews()

            override fun saveCallResult(response: List<Article>?) {
                val newsList = ArrayList<LocalNewsEntity>()
                if (response != null){
                    for (newsResponse in response){
                        val news = LocalNewsEntity(
                            title = newsResponse.title,
                            content = newsResponse.content,
                            description = newsResponse.description,
                            author = newsResponse.author,
                            urlToImage = newsResponse.urlToImage,
                            url = newsResponse.url,
                            publishedAt = newsResponse.publishedAt,
                            bookmarked = false,
                            id = 0
                        )
                        newsList.add(news)
                    }

                    localDataSource.insertIndonesiaNewsList(newsList)
                }
            }
        }.asLiveData()
    }


    override fun getBookmarkedNews(): LiveData<List<LocalNewsEntity>> = localDataSource.getBookmarkedNewsList()

    override fun insertIndonesiaNewsList(news: List<LocalNewsEntity>) {
        val runnable = {
            if (localDataSource.getIndonesiaNewsList().value.isNullOrEmpty()){
                localDataSource.insertIndonesiaNewsList(news)
            }
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun setBookmarked(news: LocalNewsEntity, bookmarked: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setBookmarkNews(news, bookmarked) }
    }
}