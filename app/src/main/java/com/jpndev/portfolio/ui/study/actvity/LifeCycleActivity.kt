package com.jpndev.portfolio.ui.study.actvity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.jpndev.portfolio.R
import com.jpndev.portfolio.databinding.ActivityAddPitemBinding
import com.jpndev.portfolio.databinding.ActivityLifeCycleBinding
import com.jpndev.portfolio.databinding.ActivityVideoPlayBinding
import com.jpndev.portfolio.ui.pmanage.AddPItemViewModel
import com.jpndev.portfolio.ui.pmanage.AddPItemViewModelFactory
import com.jpndev.portfolio.utils.LogUtils
import com.jpndev.portfolio.utils.PrefUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


const val pie:Float = 3.14F

@AndroidEntryPoint
class LifeCycleActivity : AppCompatActivity() {


    @Inject
    lateinit var factory: LifeCycleViewModelFactory
    lateinit var viewModel: LifeCycleViewModel
    private lateinit var binding: ActivityLifeCycleBinding

    @Inject
    lateinit var prefUtils: PrefUtils


    private val a by lazy(LazyThreadSafetyMode.NONE) {
        10
    }

    private val b by lazy(LazyThreadSafetyMode.NONE) {
        10.009900
    }

    private val c by lazy(LazyThreadSafetyMode.NONE) {
        99999999999999999999.00990077777777
    }
    private val d by lazy(LazyThreadSafetyMode.NONE) {
        -99999999999999999999.00990077777777
    }

    var count = 0

    val pie2 by lazy(LazyThreadSafetyMode.NONE) {
        "Photo_" + System.currentTimeMillis()
    }
    val FILENAME: String
        get() = "Img_" + System.currentTimeMillis() + ".jpeg"

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        onTextUpdate("onNewIntent")
        binding?.viewmodel?.heading?.value = "onNewIntent"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifeCycleBinding.inflate(layoutInflater)
        //creditCardNumber=88989L

        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(LifeCycleViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        //count.

        runTimer()
        //   viewModel.run_text.value="a= "+pie2+"b= "+b+"c= "+c+"d= "+d

        //   viewModel.run_text.value=   viewModel.run_text.value+"\n"

        // viewModel.run_text.value= viewModel.run_text.value


        onTextUpdate("\nOnCreate")
        //viewModel. text.value=viewModel.text.value+"\n OnCreate"

        binding.saveBtn.setOnClickListener {

            viewModel.showPManageActivity(activity = this)

        }
        binding.closeDimv.setOnClickListener {

            prefUtils.clear()
            LogUtils.LOGD("pref_lc",
                    "\n pref = " + prefUtils.getString("lifecycle", "Nothing found"))
            viewModel.showLifeCycleActivity(activity = this)

        }
        binding.lifecyleTxv.setOnClickListener {

            prefUtils.save("lifecycle", "jp " + "lifecyle= " + ++count)

            LogUtils.LOGD("pref_lc",
                    "\n pref = " + prefUtils.getString("lifecycle", "Nothing found"))


        }
    }

    fun onTextUpdate(text: String) {

        viewModel.text.value = viewModel.text.value + "\n" + " " + text
        // LogUtils.LOGD("lifecycle","\n "+text)
        viewModel.addLog("\n VM= " + viewModel.text.value, "lifecycle")
        // LogUtils.LOGD("lifecycle","\n VM= "+ viewModel. text.value)
    }

    override fun onStart() {
        super.onStart()
        onTextUpdate("onStart")
        //viewModel. text=viewModel.text+"\n onStart"
    }

    override fun onResume() {
        super.onResume()

        onTextUpdate("onResume")
    }

    override fun onPause() {
        super.onPause()
        onTextUpdate("onPause")
    }

    override fun onStop() {
        super.onStop()
        onTextUpdate("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        onTextUpdate("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        onTextUpdate("onSaveInstanceState")
        // viewModel. text=viewModel.text+"\n onSaveInstanceState"
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        onTextUpdate("onSaveInstanceState 2")
        //  viewModel. text=viewModel.text+"\n onSaveInstanceState 2"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?,
                                        persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        onTextUpdate("onRestoreInstanceState 2")
        //viewModel. text=viewModel.text+"\n onRestoreInstanceState"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        onTextUpdate("onRestoreInstanceState ")
        //viewModel. text=viewModel.text+"\n onRestoreInstanceState 2"
    }

    override fun onRestart() {
        super.onRestart()
        onTextUpdate("6666 onRestart")
        // viewModel. text=viewModel.text+"\n onRestart "
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onTextUpdate("onConfigurationChanged")
    }

    private fun runTimer() {



        // Creates a new Handler
        val handler = Handler()

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(object : Runnable {
            override fun run() {

                count=count+1


                // Set the text view text.
                binding?.head2Txv?.setText("INNER = H" +count)


                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000)
            }
        })
    }

}