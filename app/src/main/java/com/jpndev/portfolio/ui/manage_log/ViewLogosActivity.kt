package com.jpndev.portfolio.ui.manage_log

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.jpndev.portfolio.BuildConfig
import com.jpndev.portfolio.R
import com.jpndev.portfolio.databinding.ActivityLifeCycleBinding
import com.jpndev.portfolio.databinding.ActivityViewLogosBinding
import com.jpndev.portfolio.ui.study.actvity.LifeCycleViewModel
import com.jpndev.portfolio.ui.study.actvity.LifeCycleViewModelFactory
import com.jpndev.portfolio.utils.LogUtils
import com.jpndev.portfolio.utils.PrefUtils
import com.jpndev.portfolio.utils.ToastHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewLogosActivity : AppCompatActivity() {


    @Inject
    lateinit var  factory: ViewLogosViewModelFactory
    lateinit var viewModel: ViewLogosViewModel
    private lateinit var binding: ActivityViewLogosBinding

    @Inject
    lateinit var  prefUtils: PrefUtils

    var count=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewLogosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this,factory).get(ViewLogosViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner = this
        // setContentView(R.layout.activity_life_cycle)

        //viewModel. text.value=viewModel.text.value+"\n OnCreate"



        setClicks()
    }

    private fun setClicks() {

        binding.saveBtn.setOnClickListener{

            viewModel.showPManageActivity(activity = this)

        }

        binding.logosTxv.setOnClickListener{

            viewModel.usecase.logsource.addLog("VLA logosTxv click ")
            //prefUtils.save("lifecycle","jp "+"lifecyle= "+      ++count)

            //LogUtils.LOGD("pref_lc","\n pref = "+ prefUtils.getString("lifecycle","Nothing found"))


        }
        binding.closeDimv.setOnClickListener{

            onBackPressed()

        }
        binding.refreshDimv.setOnClickListener(View.OnClickListener {
            viewModel.reFresh()
        }

        )
       binding. deleteDimv.setOnClickListener {
            viewModel.deleteLogs()
        }
        if(BuildConfig.isShowApi)
        {

            binding.  jsonReqDimv.visibility = View.VISIBLE
            binding.  jsonReqDimv.setOnClickListener {
                var clipboardManager = getSystemService(
                        Context.CLIPBOARD_SERVICE) as ClipboardManager?

                val separator = "\n"

                val text =viewModel.text.value

                var clipData = ClipData.newPlainText("Json Requests  ",text)

                //   clipboardManager!!.primaryClip = clipData
                clipboardManager!!.setPrimaryClip(clipData)
                // jsontest=""
                ToastHandler.newInstance(this@ViewLogosActivity).mustShowToast("Copied Json data and cleared")

            }

        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }


}