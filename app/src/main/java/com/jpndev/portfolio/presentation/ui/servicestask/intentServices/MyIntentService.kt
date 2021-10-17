package com.jpndev.portfolio.presentation.ui.servicestask.intentServices

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jpndev.portfolio.R
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.portfolio.presentation.ui.servicestask.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyIntentService: IntentService("MyIntentService") {
    @Inject
    lateinit var  logSourceImpl: LogSourceImpl
    companion object {
        private const val TAG = "MyIntentService"
    }

    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onCreate() {
        super.onCreate()

        logSourceImpl.addLog("IS onCreate :"+Thread.currentThread().name)
        // Acquire a wakelock to keep the cpu running when the screen is OFF
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BackgroundApp:Wakelock")
        wakeLock.acquire(10*60*1000L /*10 minutes*/)

        logSourceImpl.addLog("IS onCreate  Wakelock acquired :"+Thread.currentThread().name)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = NotificationCompat.Builder(this, Constants.channelID)
                .setContentTitle(Constants.foregroundIntentServiceNotificationTitle)
                .setContentText("Running...")
               // .setSmallIcon(R.drawable.ic_android_24dp)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()

            startForeground(1, notification)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        // Executes on a single background thread in sequential manner

        logSourceImpl.addLog("IS onHandleIntent :"+Thread.currentThread().name)
        val input = intent?.getStringExtra(Constants.inputExtra)

        for(i in 0..10) {
            Log.d(TAG, "$input - $i")
            SystemClock.sleep(1000)
        }
    }

    override fun onDestroy() {

        
        wakeLock.release()
        logSourceImpl.addLog("IS onDestroy: Wakelock released :"+Thread.currentThread().name)
        super.onDestroy()
    }

}