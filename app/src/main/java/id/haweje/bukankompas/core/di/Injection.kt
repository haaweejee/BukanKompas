package id.haweje.bukankompas.core.di

import android.content.Context
import id.haweje.bukankompas.core.data.NewsRepository
import id.haweje.bukankompas.core.data.source.local.LocalDataSource
import id.haweje.bukankompas.core.data.source.local.room.NewsDatabase
import id.haweje.bukankompas.core.data.source.remote.RemoteDataSource
import id.haweje.bukankompas.core.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : NewsRepository {

        val database = NewsDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.newsDao())
        val appExecutors = AppExecutors()

        return NewsRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}