package com.jpndev.portfolio.presentation.ui.servicestask.foregroundservices

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jpndev.portfolio.BuildConfig
import com.jpndev.portfolio.databinding.ActivityJobIntentServiceBinding
import com.jpndev.portfolio.databinding.ActivityServiceMainBinding
import com.jpndev.portfolio.presentation.ui.servicestask.Constants
import com.jpndev.portfolio.R
import com.jpndev.portfolio.presentation.ui.servicestask.intentServices.IntentServiceActivity
import com.jpndev.portfolio.presentation.ui.servicestask.jobIntentServices.JobIntentServiceActivity
import com.jpndev.portfolio.presentation.ui.servicestask.jobScheduler.JobSchedulerActivity
import com.jpndev.portfolio.ui.manage_log.ViewLogosActivity
import com.jpndev.portfolio.ui.pmanage.PManageActivity


class MainFServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClicks()
        // setContentView(R.layout.activity_job_intent_service)
    }

    private fun setClicks() {
        updateTextStatus()
        binding.jobIntentServiceCard.setOnClickListener {
            val intent = Intent(this@MainFServiceActivity, JobIntentServiceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }
        binding.jobSechularCard.setOnClickListener {
            val intent = Intent(this@MainFServiceActivity, JobSchedulerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }
        binding.intentServiceCard.setOnClickListener {
            val intent = Intent(this@MainFServiceActivity, IntentServiceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }
        binding.foregroundCard.setOnClickListener {
            val intent = Intent(this@MainFServiceActivity, MainFServiceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }
    }
    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_service_main)
     }*/

    public fun startService(view: View) {
        val input = binding.editTextInput.text.toString()

        val myServiceIntent = Intent(this, MyForegroundService::class.java)
        myServiceIntent.putExtra(Constants.inputExtra, input)
        ContextCompat.startForegroundService(this, myServiceIntent)
        updateTextStatus()
    }

    public fun stopService(view: View) {
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        stopService(serviceIntent)
        updateTextStatus()
    }
    private fun updateTextStatus() {
        if(isMyServiceRunning(MyForegroundService::class.java)){
            binding.txv.text = "Service is Running"
        }else{
            binding.txv.text = "Service is NOT Running"
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    companion object{
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }

}