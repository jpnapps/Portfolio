package com.jpndev.portfolio.presentation.ui.servicestask.jobScheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyJobScheduler: JobService() {

    @Inject
    lateinit var  logSourceImpl: LogSourceImpl

    companion object {
        private const val TAG = "MyJobScheduler"
    }

    private var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {

        logSourceImpl.addLog("JS onStartJob :"+Thread.currentThread().name)
        doBackgroundJob(params)
        return true
    }

    @Throws
    private fun doBackgroundJob(params: JobParameters?) {

        logSourceImpl.addLog("JS doBackgroundJob before:"+Thread.currentThread().name)

        Thread(Runnable {
            for(i in 0..10) {
                Log.d(TAG, "run: $i")
                if(jobCancelled) {
                    return@Runnable
                }
                Thread.sleep(1000)
            }
            logSourceImpl.addLog("JS doBackgroundJob Job finished:"+Thread.currentThread().name)

            jobFinished(params, false)
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        logSourceImpl.addLog("JS onStopJob cancelled:"+Thread.currentThread().name)
        jobCancelled = true
        return true
    }
}

