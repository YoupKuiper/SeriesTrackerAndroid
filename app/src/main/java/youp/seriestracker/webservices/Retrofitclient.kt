package youp.seriestracker.webservices

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    var retrofit: Retrofit? = null


    val client: Retrofit
        get() {
//            val baseUrl = "https://series-tracker-api.herokuapp.com/"
            val baseUrl = "http://192.168.2.19:3000/"

            if (retrofit == null) {

                val gson = GsonBuilder()
                        .setLenient()
                        .create()

                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit!!
        }

}