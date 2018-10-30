package youp.seriestracker.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobService
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.evernote.android.job.JobRequest
import com.evernote.android.job.Job.Params
import android.support.annotation.NonNull
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.Job
import com.evernote.android.job.DailyJob.DailyJobResult
import javax.xml.datatype.DatatypeConstants.HOURS
import com.evernote.android.job.DailyJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.models.SeriesResponse
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient
import java.util.concurrent.TimeUnit


class MyDailyJob : DailyJob(){

    override fun onRunDailyJob(params: Params): DailyJobResult {
        println("running job")
        Thread(Runnable {

            val retrofit = RetrofitClient.client

            val service = retrofit.create(APIService::class.java)

            val call = service.checkForNewEpisodes("youpkuiper@gmail.com")
            call.enqueue(object : Callback<SeriesResponse> {
                override fun onResponse(call: Call<SeriesResponse>?, response: Response<SeriesResponse>?) {
                    if (response != null && response.isSuccessful && response.body()?.series!!.isNotEmpty()) {
                        val seriesName = response.body()?.series!![0].name
                        println(seriesName)

                        val CHANNEL_ID = "test"
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //Create notification channel
                            val context = getContext()
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
                        var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                        //Create notification builder
                        var mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
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
        }).start()
        return DailyJobResult.SUCCESS
    }

    companion object {

        val TAG = "MyDailyJob"

        fun schedule() {
            // schedule between 10 and 11 AM
            println("Scheduling job between 10 and 11 AM")
            DailyJob.scheduleAsync(JobRequest.Builder(TAG).setRequiredNetworkType(JobRequest.NetworkType.CONNECTED), TimeUnit.HOURS.toMillis(10), TimeUnit.HOURS.toMillis(11))
        }
    }
}

