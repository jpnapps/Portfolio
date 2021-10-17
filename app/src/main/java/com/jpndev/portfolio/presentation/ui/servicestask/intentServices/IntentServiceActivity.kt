package com.jpndev.portfolio.presentation.ui.servicestask.intentServices

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jpndev.portfolio.databinding.ActivityIntentServiceBinding
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.inputExtra

class IntentServiceActivity : AppCompatActivity() {


    private lateinit var binding: ActivityIntentServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.activity_intent_service)
    }



    public fun startService(view: View) {
        val input = binding.editTextInput.text.toString()

        val serviceIntent = Intent(this, MyIntentService::class.java)
        serviceIntent.putExtra(inputExtra, input)

        ContextCompat.startForegroundService(this, serviceIntent)
    }
}