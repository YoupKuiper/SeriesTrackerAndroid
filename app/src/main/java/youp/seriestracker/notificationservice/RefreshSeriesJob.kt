package youp.seriestracker.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.DailyJob
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import youp.seriestracker.R
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesList
import youp.seriestracker.utilities.config
import youp.seriestracker.webservices.APIService
import youp.seriestracker.webservices.RetrofitClient
import java.util.concurrent.TimeUnit



class RefreshSeriesJob : Job() {
    override fun onRunJob(params: Params): Result {

        Thread(Runnable {
            //Refresh the series that are currently saved
            val gson = Gson()
            val mPrefs = context!!.getSharedPreferences(config.SERIES_PREFERENCE, Context.MODE_PRIVATE)
            val mySeries = mPrefs.all
            val seriesValues = mySeries.values
            val seriesString = seriesValues.toString()
            val seriesList = gson.fromJson(seriesString, SeriesList::class.java)

            //Refresh series 20 at a time
            val retrofit = RetrofitClient.client
            val service = retrofit.create(APIService::class.java)

            for (i in seriesList.indices){
                // for every 39 calls, sleep 10 seconds
                if(i % 39 == 0){
                    Thread.sleep(11_000)
                }
                var call = service.getDetailsById(seriesList[i].id!!, config.API_KEY)
                call.enqueue(object : Callback<Series> {
                    override fun onResponse(call: Call<Series>?, response: Response<Series>?) {
                        if (response != null && response.isSuccessful) {
                            //Adding series to sharedprefs
                            val detailedSeries = response.body()
                            val prefsEditor = mPrefs.edit()
                            val json = gson.toJson(detailedSeries)
                            prefsEditor.putString(detailedSeries!!.id.toString(), json).apply()
                            println(detailedSeries!!.name)
                        } else {
                            println("No series returned")
                        }
                    }

                    override fun onFailure(call: Call<Series>?, t: Throwable?) {

                    }
                })
            }

            //Test to create a notification every time regardless of if theres any series airing today
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

            var mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_home_black_24dp)
                    .setContentTitle("Elke twee of drie dagen refresh notificatie")
                    .setContentText("Elke twee of drie dagen refresh functie aangeroepen")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

            //Create notification
            var notification = mBuilder.build()

            //Send notification
            notificationManager.notify(999, notification)
        }).start()
        return Job.Result.SUCCESS
    }


    companion object {

        val TAG = "RefreshSeriesJob"

        fun schedule() {
            // schedule between 10 and 11 AM
            println("Scheduling job every 2 or 3 days")
//            JobRequest.Builder(TAG)
//                    .setPeriodic(TimeUnit.HOURS.toMillis(), TimeUnit.DAYS.toMillis(2))
//                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
//                    .build()
//                    .schedule()
            DailyJob.scheduleAsync(JobRequest.Builder(MyDailyJob.TAG).setRequiredNetworkType(JobRequest.NetworkType.CONNECTED), TimeUnit.HOURS.toMillis(10), TimeUnit.HOURS.toMillis(11))

//            DailyJob.startNowOnce(JobRequest.Builder(TAG))
        }
    }
}