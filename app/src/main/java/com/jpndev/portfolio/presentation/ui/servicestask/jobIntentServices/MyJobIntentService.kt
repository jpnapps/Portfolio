package com.jpndev.portfolio.presentation.ui.servicestask.jobIntentServices

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.app.JobIntentService
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.inputExtra
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyJobIntentService: JobIntentService() {
    @Inject
    lateinit var  logSourceImpl: LogSourceImpl
    companion object {
        const val TAG = "MyJobIntentService"

        fun enqueueWork(context: Context, intent: Intent) {

            enqueueWork(context, MyJobIntentService::class.java, 123, intent)
        }
    }

    override fun onCreate() {
        super.onCreate()

        logSourceImpl.addLog("JIS onCreate :"+Thread.currentThread().name)
    }

    override fun onHandleWork(intent: Intent) {

        logSourceImpl.addLog("JIS onHandleWork :"+Thread.currentThread().name)
        val input = intent.getStringExtra(inputExtra)

        for(i in 0..10) {
            Log.d(TAG, "$input - $i")
            if(isStopped) return
            SystemClock.sleep(1000)
        }
    }

    override fun onStopCurrentWork(): Boolean {
        logSourceImpl.addLog("JIS onStopCurrentWork :"+Thread.currentThread().name)
        return super.onStopCurrentWork()
    }

    override fun onDestroy() {
        logSourceImpl.addLog("JIS onDestroy :"+Thread.currentThread().name)
        super.onDestroy()
    }
}