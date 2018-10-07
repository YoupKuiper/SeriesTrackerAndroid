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
    fun listSeries(@Query("name") name : String): Call<Series>

    @POST("Users/Login")
    fun login(@Body user: User): Call<User>

    @POST("Users/Register")
    fun register(@Body user: User): Call<User>

}