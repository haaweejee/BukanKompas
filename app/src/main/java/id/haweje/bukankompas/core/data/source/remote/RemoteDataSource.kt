package id.haweje.bukankompas.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.haweje.bukankompas.core.data.source.remote.network.RetrofitClient
import id.haweje.bukankompas.core.data.source.remote.response.Article
import id.haweje.bukankompas.core.data.source.remote.response.NewsResponse
import id.haweje.bukankompas.core.utils.Constanta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object{

        @Volatile
        private var instance : RemoteDataSource? = null

        fun getInstance() : RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource()
            }
    }

    fun getIndonesiaNews() : LiveData<ApiResponse<List<Article>>> {
        val resultIndonesiaNews = MutableLiveData<ApiResponse<List<Article>>>()
        RetrofitClient.apiInstance
            .getAllNewsIndonesia()
            .enqueue(object : Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    resultIndonesiaNews.value = ApiResponse.success(response.body()!!.articles)
                    Log.d(Constanta.SUCCESS, response.code().toString())
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d(Constanta.FAIL, t.message.toString())
                }
            })
        return resultIndonesiaNews
    }

    fun getIndonesiaTechNews() : LiveData<ApiResponse<List<Article>>>{
        val resultIndonesiaTechNews = MutableLiveData<ApiResponse<List<Article>>>()
        RetrofitClient.apiInstance
            .getAllTechNewsIndonesia()
            .enqueue(object : Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    resultIndonesiaTechNews.value = ApiResponse.success(response.body()!!.articles)
                    Log.d(Constanta.SUCCESS, response.code().toString())
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d(Constanta.FAIL, t.message.toString())
                }
            })
        return resultIndonesiaTechNews
    }

    interface loadNewsListCallback{
        fun onLoadNews(response: List<Article>?)
    }
}

