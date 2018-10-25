package youp.seriestracker.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.adapters.SeriesAdapter
import youp.seriestracker.models.Body
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesResponse
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient

class DetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val series = intent.getParcelableExtra<Series>("SERIES")
        val mySeriesIds = intent.getIntegerArrayListExtra("MY_SERIES_IDS")
        val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

        Picasso.get().load(BASE_IMG_URL + series.backdropPath).into(series_image)
        series_name.text = series.name
        article_description.text = series.overview
        checkbox.isChecked = mySeriesIds.contains(series.id!!)
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox -> {
                    val retrofit = RetrofitClient.client
                    val service = retrofit.create(APIService::class.java)

                    if (checked) {
                        // Add to my series
                        val call = service.addSeriesToMySeries(intent.getParcelableExtra<Series>("SERIES"))
                        call.enqueue(object : Callback<SeriesResponse> {
                            override fun onResponse(call: Call<SeriesResponse>?, response: Response<SeriesResponse>?) {
                                if (response != null && response.isSuccessful) {
                                    println(response)
                                } else {
                                    println("No series returned")
                                }
                            }

                            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
                            }
                        })

                    } else {
                        // Remove from my series
                        val call = service.deleteSeriesFromMySeries(intent.getParcelableExtra<Series>("SERIES").id!!)
                        call.enqueue(object : Callback<SeriesResponse> {
                            override fun onResponse(call: Call<SeriesResponse>?, response: Response<SeriesResponse>?) {
                                if (response != null && response.isSuccessful) {
                                    println(response)
                                } else {
                                    println("No series returned")
                                }
                            }

                            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show();
                            }
                        })

                    }
                }
            }
        }
    }
}
