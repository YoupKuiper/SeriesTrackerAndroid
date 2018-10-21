package youp.seriestracker.activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
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
import youp.seriestracker.notificationservice.NotificationJobService
import youp.seriestracker.notificationservice.NotificationUtils
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false

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

        scheduleJob()
        if (!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this@MainActivity)
        }
    }

    fun scheduleJob(){
        val componentName = ComponentName(this, NotificationJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
                .setPersisted(true)
                .setPeriodic(5 * 1000)
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
