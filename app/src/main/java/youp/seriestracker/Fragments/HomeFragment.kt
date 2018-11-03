package youp.seriestracker.Fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.view.*
import youp.seriestracker.R
import youp.seriestracker.activities.DetailActivity
import youp.seriestracker.adapters.SeriesAdapter
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesList
import youp.seriestracker.utilities.config
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient


class HomeFragment : Fragment() {

    lateinit var seriesList: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false);

        seriesList = rootView.seriesList
        val gson = Gson()

        val retrofit = RetrofitClient.client
        val service = retrofit.create(APIService::class.java)

        seriesList.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var series = seriesList.getItemAtPosition(position)

                //Turn series into json string
                val seriesString = gson.toJson(series)

                //Go to detail activity, pass clicked item
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("SERIES", seriesString)
                }
                startActivity(intent)
            }
        }

        val mPrefs = context!!.getSharedPreferences(config.SERIES_PREFERENCE, MODE_PRIVATE)
        val mySeries = mPrefs.all
        val seriesValues = mySeries.values
        val seriesString = seriesValues.toString()
        val series = gson.fromJson(seriesString, SeriesList::class.java)
        val orderedSeries = series.sortedWith(compareBy({ it.name }))
        seriesList.adapter = SeriesAdapter(activity!!.applicationContext, orderedSeries)

        // Inflate the layout for this fragment
        return rootView
    }

    fun refresh(){
        val mPrefs = context!!.getSharedPreferences(config.SERIES_PREFERENCE, MODE_PRIVATE)
        val mySeries = mPrefs.all
        val seriesValues = mySeries.values
        val seriesString = seriesValues.toString()
        val gson = Gson()
        val series = gson.fromJson(seriesString, SeriesList::class.java)
        val orderedSeries = series.sortedWith(compareBy({ it.name }))
        seriesList.adapter = SeriesAdapter(activity!!.applicationContext, orderedSeries)
    }

    fun filterSeries(searchString: String, series: List<Series>): List<Series>{
        return series.filter { s -> s.name!!.contains(searchString) }
    }
}