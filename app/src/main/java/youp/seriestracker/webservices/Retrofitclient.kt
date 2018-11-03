package youp.seriestracker.webservices

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    var retrofit: Retrofit? = null


    val client: Retrofit
        get() {
//            val baseUrl = "https://series-tracker-api.herokuapp.com/"
//            val baseUrl = "http://192.168.2.8:3000/"
            val baseUrl = "https://api.themoviedb.org/3/"

            if (retrofit == null) {

                val gson = GsonBuilder()
                        .setLenient()
                        .create()

                val okHttpClient = OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build()

                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit!!
        }

}