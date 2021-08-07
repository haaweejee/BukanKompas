package id.haweje.bukankompas.core.data.source.remote.network

import id.haweje.bukankompas.core.data.source.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    fun getAllNewsIndonesia(
        @Query("country") countryCode : String = "id",
        @Query("apiKey") apiKey : String = "996c55497a7e444494bccc36ff3cb97a"
    ): Call<NewsResponse>

    @GET("v2/top-headlines")
    fun getAllTechNewsIndonesia(
        @Query("country") countryCode : String = "id",
        @Query("category") category : String = "technology",
        @Query("apiKey") apiKey : String = "996c55497a7e444494bccc36ff3cb97a"
    ): Call<NewsResponse>
}