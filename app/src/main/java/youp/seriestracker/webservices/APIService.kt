package youp.seriestracker.webservices

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import youp.seriestracker.Models.User
import youp.seriestracker.models.Series

interface APIService {
    @GET("Series/GetDetailsByName")
    fun searchByName(@Query("name") name : String): Call<List<Series>>

    @GET("Series")
    fun listSeries(): Call<List<Series>>

    @GET("Series/CheckForNewEpisodes")
    fun checkForNewEpisodes(@Query("username") name : String) : Call<List<Series>>

    @POST("Users/Login")
    fun login(@Body user: User): Call<User>

    @POST("Users/Register")
    fun register(@Body user: User): Call<User>

}