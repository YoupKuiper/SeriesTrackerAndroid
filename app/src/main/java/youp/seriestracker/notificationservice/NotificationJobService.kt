package youp.seriestracker.notificationservice

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesResponse
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient

class NotificationJobService : JobService() {

    var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        println("BackgroundJob Started")
        doBackgroundJob(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        println("BackgroundJob Stopped")
        jobCancelled = true
        return false
    }
    private fun doBackgroundJob(params: JobParameters?) {
        Thread(Runnable {

            val retrofit = RetrofitClient.client

            val service = retrofit.create(APIService::class.java)

            val call = service.checkForNewEpisodes("youpkuiper@gmail.com")
            call.enqueue(object : Callback<SeriesResponse> {
                override fun onResponse(call: Call<SeriesResponse>?, response: Response<SeriesResponse>?) {
                    if (response != null && response.isSuccessful) {
                        val seriesName = response.body()?.series!![0].name
                        println(seriesName)
                    } else {
                        println("No episodes found for today")
                    }
                }

                override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                    println("Checking for new episodes failed")
                }
            })
            jobFinished(params, false)
        }).start()
    }
}