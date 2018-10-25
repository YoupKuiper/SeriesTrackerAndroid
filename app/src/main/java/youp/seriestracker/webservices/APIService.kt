package youp.seriestracker.webservices

import retrofit2.Call
import retrofit2.http.*
import youp.seriestracker.Models.User
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesResponse

interface APIService {
    @GET("Series/GetDetailsByName")
    fun searchByName(@Query("name") name : String): Call<SeriesResponse>

    @GET("Series")
    fun listSeries(): Call<SeriesResponse>

    @GET("Series/CheckForNewEpisodes")
    fun checkForNewEpisodes(@Query("username") name : String) : Call<SeriesResponse>

    @POST("Series")
    fun addSeriesToMySeries(@Body series: Series) : Call<SeriesResponse>

    @POST("Users/Login")
    fun login(@Body user: User): Call<User>

    @POST("Users/Register")
    fun register(@Body user: User): Call<User>

    @DELETE("Series")
    fun deleteSeriesFromMySeries(@Query("seriesId") seriesId: Int) : Call<SeriesResponse>

}