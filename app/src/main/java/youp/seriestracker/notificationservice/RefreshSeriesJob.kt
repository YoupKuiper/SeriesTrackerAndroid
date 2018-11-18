package youp.seriestracker.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import youp.seriestracker.R
import java.util.concurrent.TimeUnit



class RefreshSeriesJob : Job() {
    override fun onRunJob(params: Params): Result {

        Thread(Runnable {
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
            JobRequest.Builder(TAG)
                    .setExecutionWindow(TimeUnit.DAYS.toMillis(2), TimeUnit.DAYS.toMillis(3))
                    .build()
                    .schedule()
//                    .scheduleAsync(JobRequest.Builder(TAG).setRequiredNetworkType(JobRequest.NetworkType.CONNECTED), TimeUnit.HOURS.toMillis(10), TimeUnit.HOURS.toMillis(11))

//            DailyJob.startNowOnce(JobRequest.Builder(TAG))
        }
    }
}