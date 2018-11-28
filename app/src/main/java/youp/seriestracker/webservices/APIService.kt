package youp.seriestracker.webservices

import retrofit2.Call
import retrofit2.http.*
import youp.seriestracker.Models.User
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesResponse
import youp.seriestracker.models.SimpleSeries
import youp.seriestracker.models.SimpleSeriesResponse

interface APIService {

    @GET("search/tv")
    fun searchByName(@Query("api_key") apiKey: String, @Query("query",encoded = true) query: String) : Call<SimpleSeriesResponse>

    @GET("Series")
    fun listSeries(): Call<SeriesResponse>

    @GET("Series/CheckForNewEpisodes")
    fun checkForNewEpisodes(@Query("username") name : String) : Call<SeriesResponse>

    @GET("tv/{id}")
    fun getDetailsById(@Path("id") id: Int, @Query("api_key") apiKey: String): Call<Series>

    @POST("Series")
    fun addSeriesToMySeries(@Body series: SimpleSeries) : Call<SeriesResponse>

    @POST("Users/Login")
    fun login(@Body user: User): Call<User>

    @POST("Users/Register")
    fun register(@Body user: User): Call<User>

    @DELETE("Series")
    fun deleteSeriesFromMySeries(@Query("seriesId") seriesId: Int) : Call<SeriesResponse>

}