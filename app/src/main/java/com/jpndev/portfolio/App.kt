package com.jpndev.portfolio

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var   logSource: LogSourceImpl

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )

    }
    override fun onTerminate() {
        super.onTerminate()
        logSource.addLog("app onTerminate   Thread= "+Thread.currentThread().name)
    }

    suspend open fun addLog(obj: Any?) {

   /*     coroutineScope {*/

            try {


                if (obj is String) {

                    logSource.addLog("\n Text  = " +obj)


                }



            } catch (e: Exception) {
                // activity.hideProgress()
            }


        }



    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(true)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager =getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }

    }
}
