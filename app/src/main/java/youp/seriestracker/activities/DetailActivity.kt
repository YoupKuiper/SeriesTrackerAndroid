package youp.seriestracker.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import youp.seriestracker.R
import youp.seriestracker.models.Body
import youp.seriestracker.models.Series

class DetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val series = intent.getParcelableExtra<Series>("SERIES")
        val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

        Picasso.get().load(BASE_IMG_URL + series.backdropPath).into(series_image)
        series_name.text = series.name


    }
}
