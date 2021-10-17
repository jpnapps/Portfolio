package com.jpndev.portfolio.presentation.ui.servicestask.foregroundservices

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jpndev.portfolio.R
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.portfolio.presentation.ui.servicestask.Constants
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.channelID
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.foregroundServiceNotificationTitle
import com.jpndev.portfolio.presentation.ui.servicestask.intentServices.MyIntentService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MyForegroundService: Service() {
    @Inject
    lateinit var  logSourceImpl: LogSourceImpl
    override fun onCreate() {
        super.onCreate()
        logSourceImpl.addLog("FS onCreate :"+Thread.currentThread().name)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra(Constants.inputExtra)
        logSourceImpl.addLog("FS onStartCommand :"+Thread.currentThread().name)
        val notificationIntent = Intent(this, MainFServiceActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle(foregroundServiceNotificationTitle)
            .setContentText(input)
          //  .setSmallIcon(R.drawable.ic_android_24dp)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
       // startForeground(1,notification, FOREGROUND_SERVICE_TYPE_LOCATION)
        loopfun()
      //  stopForeground(true)
      //  return START_NOT_STICKY
        return START_STICKY
    }
    lateinit var thread: Thread

    private fun loopfun() {
        thread= Thread(Runnable {
            for(i in 0..25) {
               // if(i%1000==0) {
                    logSourceImpl.addLog("FS loopfun $i :" + Thread.currentThread().name)
               // }
                SystemClock.sleep(1000)
            }

        })
        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
     //   thread.stop()
        logSourceImpl.addLog("FS onDestroy :"+Thread.currentThread().name)
    }

    override fun onBind(intent: Intent?): IBinder? {
        //thread.stop()
        logSourceImpl.addLog("FS onBind :"+Thread.currentThread().name)
        return null
    }
}