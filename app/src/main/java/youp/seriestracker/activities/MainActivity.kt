package youp.seriestracker.activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import youp.seriestracker.R
import youp.seriestracker.notificationservice.NotificationJobService
import java.util.*
import youp.seriestracker.Fragments.HomeFragment
import youp.seriestracker.Fragments.SearchFragment
import youp.seriestracker.Fragments.SettingsFragment
import android.widget.SearchView
import youp.seriestracker.models.Series
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false
    var mySeriesIds: ArrayList<Int> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val mainFragment = HomeFragment()

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, mainFragment, "HOME_FRAGMENT")
        transaction.addToBackStack(null)
        transaction.commit()

        scheduleJob()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                val homeFragment = supportFragmentManager.findFragmentByTag("HOME_FRAGMENT")
                val searchFragment = supportFragmentManager.findFragmentByTag("SEARCH_FRAGMENT")
                if(homeFragment != null && homeFragment.isVisible){
                    // Search home fragment
                    if(homeFragment is HomeFragment){
//                        homeFragment.filterSeries(s)
                    }

                }else if(searchFragment != null && searchFragment.isVisible){
                    // Search search fragment
                    if(searchFragment is SearchFragment){
                        searchFragment.searchSeries(s)
                    }
                }

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return true
    }



    fun getCurrentlyVisibleFragmentTag(){

    }

    fun scheduleJob(){

        val componentName = ComponentName(this, NotificationJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
                .setPersisted(true)
                .setPeriodic(24 * 60 * 60 * 1000L)
                .build()

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(info)
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            println("Job Scheduled...")
        } else{
            println("Job Scheduling Failed...")
        }
    }

    fun cancelJob(){
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(123)
        println("Job cancelled")
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val mainFragment = HomeFragment()

                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.container, mainFragment, "HOME_FRAGMENT")
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val searchFragment = SearchFragment()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.container, searchFragment, "SEARCH_FRAGMENT")
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val settingsFragment = SettingsFragment()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.container, settingsFragment, "SETTINGS_FRAGMENT")
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
