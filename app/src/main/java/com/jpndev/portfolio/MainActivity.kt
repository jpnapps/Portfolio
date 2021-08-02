package com.jpndev.portfolio

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.jpndev.portfolio.databinding.ActivityMainBinding
import com.jpndev.portfolio.presentation.ui.topqa.QAAdapter
import com.jpndev.portfolio.presentation.ui.topqa.QAViewModelFactory
import com.jpndev.portfolio.presentation.ui.topqa.TopQAViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var  factory: QAViewModelFactory
    lateinit var viewModel: TopQAViewModel


    @Inject
    lateinit var qa_adpater: QAAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this,factory).get(TopQAViewModel::class.java)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController= navHostFragment.navController
     //   val navController = findNavController(R.id.fragmentContainerView)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
       // setupActionBarWithNavController(navController, appBarConfiguration)
       // binding.navView.setupWithNavController(navController)
    }
}