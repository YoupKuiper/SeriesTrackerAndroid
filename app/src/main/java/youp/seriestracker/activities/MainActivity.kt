package youp.seriestracker.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.models.Series
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val retrofit = RetrofitClient.client

        val service = retrofit.create(APIService::class.java)

        val call = service.listSeries()
        call.enqueue(object : Callback<List<Series>> {
            override fun onResponse(call: Call<List<Series>>?, response: Response<List<Series>>?) {

                if (response != null && response.isSuccessful) {
                    val seriesName = response.body()!![0].body
                    Log.v("Series: ", "asd")
//                    Toast.makeText(applicationContext, seriesName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(applicationContext, "No Series Found", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<List<Series>>?, t: Throwable?) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
