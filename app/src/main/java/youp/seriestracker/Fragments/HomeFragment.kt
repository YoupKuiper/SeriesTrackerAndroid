package youp.seriestracker.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.activities.DetailActivity
import youp.seriestracker.activities.MainActivity
import youp.seriestracker.adapters.SeriesAdapter
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesResponse
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false);

        val seriesList = rootView.seriesList

        val retrofit = RetrofitClient.client
        val service = retrofit.create(APIService::class.java)

        seriesList.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val series = seriesList.getItemAtPosition(position)

                //Go to detail activity, pass clicked item
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("SERIES", series as Series)
                    val activity = activity as MainActivity
                    putIntegerArrayListExtra("MY_SERIES_IDS", activity.mySeriesIds)
                }

                startActivity(intent)
            }
        }

        val call = service.listSeries()
        call.enqueue(object : Callback<SeriesResponse> {
            override fun onResponse(call: Call<SeriesResponse>?, response: Response<SeriesResponse>?) {

                if (response != null && response.isSuccessful) {
                    val mainActivity = activity as MainActivity
                    mainActivity.mySeriesIds = response.body()?.series!!.map { s -> s.id } as ArrayList<Int>
                    seriesList.adapter = SeriesAdapter(activity!!.applicationContext, response.body()?.series)
                } else {
                    Toast.makeText(context, "No Series Found", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        })

        // Inflate the layout for this fragment
        return rootView
    }

    fun filterSeries(searchString: String, series: List<Series>): List<Series>{
        return series.filter { s -> s.name!!.contains(searchString) }
    }
}