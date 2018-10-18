package youp.seriestracker.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.models.Series
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient
import youp.seriestracker.adapters.SeriesAdapter
import youp.seriestracker.models.Body
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import youp.seriestracker.models.SeriesResponse


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val retrofit = RetrofitClient.client
        val service = retrofit.create(APIService::class.java)

        seriesList.onItemClickListener = object : OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val series = seriesList.getItemAtPosition(position)

                //Go to detail activity, pass clicked item
                val intent = Intent(applicationContext, DetailActivity::class.java).apply {
                    putExtra("SERIES", series as Series)
                }

                startActivity(intent)
            }
        }

        val call = service.listSeries()
        call.enqueue(object : Callback<SeriesResponse> {
            override fun onResponse(call: Call<SeriesResponse>?, response: Response<SeriesResponse>?) {

                if (response != null && response.isSuccessful) {
                    seriesList.adapter = SeriesAdapter(applicationContext, response.body()?.series)
                } else {
                    Toast.makeText(applicationContext, "No Series Found", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show();
            }
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
//                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
