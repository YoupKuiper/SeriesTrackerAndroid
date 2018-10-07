package youp.seriestracker.webservices

import retrofit2.Call
import retrofit2.http.GET
import youp.seriestracker.models.Series

interface APIService {
    @GET("Series/GetDetailsByName?name=atlanta")
    fun listSeries(): Call<Series>
}