package com.jpndev.portfolio.presentation.ui.servicestask.jobIntentServices

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jpndev.portfolio.databinding.ActivityJobIntentServiceBinding
import com.jpndev.portfolio.databinding.ActivityMainBinding
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.inputExtra
import com.jpndev.portfolio.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JobIntentServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobIntentServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobIntentServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setContentView(R.layout.activity_job_intent_service)
    }

    public fun enqueueWork(view: View) {
        val input =binding. editTextInput.text.toString()

        val intent = Intent(this, MyJobIntentService::class.java)
        intent.putExtra(inputExtra, input)

        MyJobIntentService.enqueueWork(this, intent)
    }
}