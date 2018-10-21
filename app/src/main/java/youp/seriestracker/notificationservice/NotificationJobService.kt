package youp.seriestracker.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
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

                        val CHANNEL_ID = "test"
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //Create notification channel
                            val context = applicationContext
                            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                            val importance = NotificationManager.IMPORTANCE_HIGH

                            val notificationChannel = NotificationChannel(CHANNEL_ID, "seriesTrackerNotificationChannel", importance)
                            notificationChannel.enableVibration(true)
                            notificationChannel.setShowBadge(true)
                            notificationChannel.enableLights(true)
                            notificationChannel.lightColor = Color.parseColor("#e8334a")
                            notificationChannel.description = "test"
                            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                            notificationManager.createNotificationChannel(notificationChannel)
                        }

                        //Get notification manager
                        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                        //Create notification builder
                        var mBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_home_black_24dp)
                                .setContentTitle(seriesName)
                                .setContentText(seriesName)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)

                        //Create notification
                        var notification = mBuilder.build()

                        //Send notification
                        notificationManager.notify(1000, notification)


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