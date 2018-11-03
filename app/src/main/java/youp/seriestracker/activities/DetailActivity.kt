package youp.seriestracker.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.models.Series
import youp.seriestracker.utilities.config
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient




class DetailActivity : Activity() {

    lateinit var series: Series

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var seriesString = intent.extras.getString("SERIES")

        val gson = Gson()
        series = gson.fromJson(seriesString, Series::class.java)

        val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

        Picasso.get().load(BASE_IMG_URL + series.backdropPath).into(series_image)
        series_name.text = series.name
        article_description.text = series.overview
        checkbox.isChecked = getSharedPreferences(config.SERIES_PREFERENCE, Context.MODE_PRIVATE).contains(series.id.toString())
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            var mPrefs = getSharedPreferences(config.SERIES_PREFERENCE, Context.MODE_PRIVATE)

            when (view.id) {
                R.id.checkbox -> {
                    val retrofit = RetrofitClient.client
                    val service = retrofit.create(APIService::class.java)

                    if (checked) {
                        // Add to my series
                        var call = service.getDetailsById(series.id!!, config.API_KEY)
                        call.enqueue(object : Callback<Series> {
                            override fun onResponse(call: Call<Series>?, response: Response<Series>?) {
                                if (response != null && response.isSuccessful) {
                                    //Adding series to sharedprefs
                                    val detailedSeries = response.body()
                                    val prefsEditor = mPrefs.edit()
                                    val gson = Gson()
                                    val json = gson.toJson(detailedSeries)
                                    prefsEditor.putString(detailedSeries!!.id.toString(), json).apply()
                                } else {
                                    println("No series returned")
                                }
                            }

                            override fun onFailure(call: Call<Series>?, t: Throwable?) {
                                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        // Remove from my series
                        mPrefs = getSharedPreferences(config.SERIES_PREFERENCE, Context.MODE_PRIVATE)
                        mPrefs.edit().remove(series.id.toString()).apply()

                    }
                }
            }
        }
    }
}
