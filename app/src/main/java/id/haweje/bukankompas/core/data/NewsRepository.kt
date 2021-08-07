package id.haweje.bukankompas.core.data

import androidx.lifecycle.LiveData
import id.haweje.bukankompas.core.data.source.local.LocalDataSource
import id.haweje.bukankompas.core.data.source.local.entity.BookmarkEntity
import id.haweje.bukankompas.core.data.source.local.entity.HeadlinesEntity
import id.haweje.bukankompas.core.data.source.local.entity.TechEntity
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

    override fun getIndonesiaNews(): LiveData<Resource<List<HeadlinesEntity>>> {
        return object : NetworkBoundResource<List<HeadlinesEntity>, List<Article>>(appExecutors){
            override fun loadFromDb(): LiveData<List<HeadlinesEntity>> = localDataSource.getIndonesiaNewsList()

            override fun shouldFetch(resultType: List<HeadlinesEntity>?): Boolean =
                resultType == null || resultType.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Article>>> =
                remoteDataSource.getIndonesiaNews()

            override fun saveCallResult(response: List<Article>?) {
                val newsList = ArrayList<HeadlinesEntity>()
                if (response != null){
                    for (newsResponse in response){
                        val news = HeadlinesEntity(
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

    override fun getIndonesiaTechNews(): LiveData<Resource<List<TechEntity>>> {
        return object : NetworkBoundResource<List<TechEntity>, List<Article>>(appExecutors){
            override fun loadFromDb(): LiveData<List<TechEntity>> = localDataSource.getIndonesiaTechNewsList()

            override fun shouldFetch(resultType: List<TechEntity>?): Boolean =
                resultType == null || resultType.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Article>>> =
                remoteDataSource.getIndonesiaTechNews()

            override fun saveCallResult(response: List<Article>?) {
                val newsList = ArrayList<TechEntity>()
                if (response != null){
                    for (newsResponse in response){
                        val news = TechEntity(
                            title = newsResponse.title,
                            content = newsResponse.content,
                            description = newsResponse.description,
                            author = newsResponse.author,
                            urlToImage = newsResponse.urlToImage,
                            url = newsResponse.url,
                            publishedAt = newsResponse.publishedAt,
                            bookmarked = false,
                            id = 0)
                        newsList.add(news)
                    }

                    localDataSource.insertIndonesiaTechNewsList(newsList)
                }
            }
        }.asLiveData()

    }

    override fun getBookmarkedNews(): LiveData<List<BookmarkEntity>> = localDataSource.getBookmarkedNewsList()

    override fun insertIndonesiaNewsTechList(news: List<TechEntity>) {
        val runnable = {
            if (localDataSource.getIndonesiaTechNewsList().value.isNullOrEmpty()){
                localDataSource.insertIndonesiaTechNewsList(news)
            }
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun insertIndonesiaNewsList(news: List<HeadlinesEntity>) {
        val runnable = {
            if (localDataSource.getIndonesiaNewsList().value.isNullOrEmpty()){
                localDataSource.insertIndonesiaNewsList(news)
            }
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun setBookmarked(news: BookmarkEntity, bookmarked: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setBookmarkNews(news, bookmarked) }
    }
}