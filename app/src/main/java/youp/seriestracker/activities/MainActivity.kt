package youp.seriestracker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response
import youp.seriestracker.models.Series
import youp.seriestracker.webservices.APIService
import retrofit2.Callback
import youp.seriestracker.R
import youp.seriestracker.webservices.RetrofitClient


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.client

        val service = retrofit.create(APIService::class.java)

        val call = service.listSeries()
        call.enqueue(object : Callback<Series> {
            override fun onResponse(call: Call<Series>?, response: Response<Series>?) {

                if (response != null && response.isSuccessful) {
                    val atlanta = response.body()!!.body!!.name;
                    Toast.makeText(applicationContext, atlanta, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(applicationContext, "No Series Found", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<Series>?, t: Throwable?) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        })


    }
}
