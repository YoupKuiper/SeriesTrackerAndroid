package youp.seriestracker.notificationservice

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class DemoJobCreator : JobCreator {

    override fun create(tag: String): Job? {
        println("creating demojobcreator")
        when (tag) {
            MyDailyJob.TAG -> return MyDailyJob()
            RefreshSeriesJob.TAG -> return RefreshSeriesJob()
            else -> return null
        }
    }
}