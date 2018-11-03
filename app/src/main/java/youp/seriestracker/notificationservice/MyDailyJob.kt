package youp.seriestracker.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.DailyJob
import com.evernote.android.job.JobRequest
import com.google.gson.Gson
import youp.seriestracker.R
import youp.seriestracker.models.Series
import youp.seriestracker.models.SeriesList
import youp.seriestracker.utilities.config
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MyDailyJob : DailyJob() {

    override fun onRunDailyJob(params: Params): DailyJobResult {
        println("running job")
        Thread(Runnable {

            val gson = Gson()
            val mPrefs = context!!.getSharedPreferences(config.SERIES_PREFERENCE, Context.MODE_PRIVATE)
            val mySeriesKP = mPrefs.all
            val seriesValues = mySeriesKP.values
            val seriesString = seriesValues.toString()
            val series = gson.fromJson(seriesString, SeriesList::class.java)

            //Get series that air today
            var todaysSeries: MutableList<Series> = arrayListOf()
            series.forEach {
                val sdf = SimpleDateFormat("yyyy-M-dd")
                val currentDate = sdf.format(Date())
                val nextEpisodeDate = it.nextEpisodeToAir?.airDate
                if (nextEpisodeDate == currentDate) {
                    todaysSeries.add(it)
                }
            }

            //If theres no series today, end the thread
            if (todaysSeries.count() > 0) {
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

                //Create notification builder for each series that airs today
                for (i in todaysSeries.indices) {
                    val seriesName = todaysSeries[i].name
                    var mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_home_black_24dp)
                            .setContentTitle(seriesName)
                            .setContentText("There's a new episode of $seriesName coming out today!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)

                    //Create notification
                    var notification = mBuilder.build()

                    //Send notification
                    notificationManager.notify(i, notification)
                }
            }
        }).start()
        return DailyJobResult.SUCCESS
    }

    companion object {

        val TAG = "MyDailyJob"

        fun schedule() {
            // schedule between 10 and 11 AM
            println("Scheduling job between 10 and 11 AM")
            DailyJob.scheduleAsync(JobRequest.Builder(TAG).setRequiredNetworkType(JobRequest.NetworkType.CONNECTED), TimeUnit.HOURS.toMillis(10), TimeUnit.HOURS.toMillis(11))
//            DailyJob.startNowOnce(JobRequest.Builder(TAG))
        }
    }
}

