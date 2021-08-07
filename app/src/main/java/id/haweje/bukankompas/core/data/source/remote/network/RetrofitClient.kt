package id.haweje.bukankompas.core.data.source.remote.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance: NewsApi = retrofit.create(NewsApi::class.java)
}