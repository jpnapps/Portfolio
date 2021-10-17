package com.jpndev.portfolio


import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jpndev.portfolio.databinding.ActivityMainBinding
import com.jpndev.portfolio.presentation.ui.MainViewModel
import com.jpndev.portfolio.presentation.ui.MainViewModelFactory
import com.jpndev.portfolio.presentation.ui.topqa.QAAdapter
import com.jpndev.portfolio.presentation.ui.topqa.QAViewModelFactory
import com.jpndev.portfolio.presentation.ui.topqa.TopQAViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var  factory: QAViewModelFactory
    lateinit var viewModel: TopQAViewModel

    @Inject
    lateinit var  mainFactory: MainViewModelFactory
    lateinit var viewMainModel: MainViewModel


    @Inject
    lateinit var qa_adpater: QAAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this,factory).get(TopQAViewModel::class.java)
        viewMainModel= ViewModelProvider(this,mainFactory).get(MainViewModel::class.java)
    /*    val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController= navHostFragment.navController
     //   val navController = findNavController(R.id.fragmentContainerView)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_more
            )
        )
    // setupActionBarWithNavController(navController, appBarConfiguration)
       binding.navView.setupWithNavController(navController)*/


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController= navHostFragment.navController
        binding.navView.setupWithNavController(
            navController
        )

      //  setBioPrompt()
      //  setBioAuth()
    }
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private fun setBioPrompt() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()

                    finish()
                }
            })
    }

    private fun setBioAuth() {
        // Allows user to authenticate using either a Class 3 biometric or
// their lock screen credential (PIN, pattern, or password).
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
            // Can't call setNegativeButtonText() and
            // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
            // .setNegativeButtonText("Use account password")

        biometricPrompt.authenticate(promptInfo)
    }
}