package com.jpndev.portfolio.presentation.ui.servicestask.jobScheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jpndev.portfolio.databinding.ActivityJobSchedulerBinding
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.jobId

class JobSchedulerActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "JobSchedulerActivity"
    }
    private lateinit var binding: ActivityJobSchedulerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobSchedulerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.activity_job_scheduler)
    }



    fun scheduleJob(view: View) {
        val componentName = ComponentName(this, MyJobScheduler::class.java)
        val jobInfo = JobInfo.Builder(jobId, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000 /* 15 minutes */)
            .build()

        val scheduler = (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler)
        val resultCode = scheduler.schedule(jobInfo)

        if(resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled")
        } else {
            Log.d(TAG, "Job scheduling failed")
        }
    }

    fun cancelJob(view: View) {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(jobId)
        Log.d(TAG, "cancelJob: Job cancelled")
    }
}