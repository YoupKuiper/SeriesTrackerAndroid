package youp.seriestracker.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.activities.DetailActivity
import youp.seriestracker.adapters.SeriesAdapter
import youp.seriestracker.models.SimpleSeriesResponse
import youp.seriestracker.utilities.config
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient

class SearchFragment : Fragment() {

    var seriesList: ListView? = null
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        val gson = Gson()
        progressBar = rootView.progressBar
        progressBar.visibility = View.GONE
        seriesList = rootView.searchSeriesList

        rootView.searchSeriesList.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val series = rootView.searchSeriesList.getItemAtPosition(position)

                //Turn series into json string
                val seriesString = gson.toJson(series)

                //Go to detail activity, pass clicked item
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("SERIES", seriesString)
                }
                startActivity(intent)
            }
        }
        return rootView
    }

    fun searchSeries(searchString: String){
        val retrofit = RetrofitClient.client
        val service = retrofit.create(APIService::class.java)
        progressBar.visibility = View.VISIBLE

        val call = service.searchByName(config.API_KEY, searchString)
        call.enqueue(object : Callback<SimpleSeriesResponse> {
            override fun onResponse(call: Call<SimpleSeriesResponse>?, response: Response<SimpleSeriesResponse>?) {
                progressBar.visibility = View.GONE
                if (response != null && response.isSuccessful) {
                    //Might need to parse the non detailed series response to a detailed series response
                    seriesList!!.adapter = SeriesAdapter(activity!!.applicationContext, response.body()?.series)
                } else {
                    Toast.makeText(context, "No Series Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SimpleSeriesResponse>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        })
    }

}
