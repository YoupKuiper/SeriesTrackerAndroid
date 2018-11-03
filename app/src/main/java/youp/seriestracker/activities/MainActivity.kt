package youp.seriestracker.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import com.evernote.android.job.JobManager
import kotlinx.android.synthetic.main.activity_main.*
import youp.seriestracker.Fragments.HomeFragment
import youp.seriestracker.Fragments.SearchFragment
import youp.seriestracker.Fragments.SettingsFragment
import youp.seriestracker.R
import youp.seriestracker.notificationservice.DemoJobCreator
import youp.seriestracker.notificationservice.MyDailyJob
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false
    var mySeriesIds: ArrayList<Int> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val mainFragment = HomeFragment()

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, mainFragment, "HOME_FRAGMENT")
        transaction.commit()

        JobManager.create(this).addJobCreator(DemoJobCreator())
        MyDailyJob.schedule()
    }

    override fun onRestart() {
        super.onRestart()
        //If homefragment is visible, refresh it
        val homeFragment = supportFragmentManager.findFragmentByTag("HOME_FRAGMENT")
        if(homeFragment is HomeFragment){
            homeFragment.refresh()
        }

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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val mainFragment = HomeFragment()

                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.container, mainFragment, "HOME_FRAGMENT")
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val searchFragment = SearchFragment()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.container, searchFragment, "SEARCH_FRAGMENT")
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val settingsFragment = SettingsFragment()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.container, settingsFragment, "SETTINGS_FRAGMENT")
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
