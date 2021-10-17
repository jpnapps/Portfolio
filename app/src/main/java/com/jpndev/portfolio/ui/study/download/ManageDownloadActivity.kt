package com.jpndev.portfolio.ui.study.download

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jpndev.portfolio.R
import com.jpndev.portfolio.databinding.ActivityManageDownloadBinding
import com.jpndev.portfolio.databinding.ActivityVideoPlayBinding
import com.jpndev.portfolio.ui.study.video.VideoPlayViewModel
import com.jpndev.portfolio.ui.study.video.VideoPlayViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.widget.Toast

import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat



import androidx.core.content.ContextCompat




@AndroidEntryPoint
class ManageDownloadActivity : AppCompatActivity() {


    @Inject
    lateinit var  factory: DownloadViewModelFactory
    lateinit var viewModel: DownloadViewModel

    var videoURL =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityManageDownloadBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_manage_download)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this,factory).get(DownloadViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner = this
      //  askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 102);
        viewModel.usecase.logsource.addLog("MDA onCreate  : "+Thread.currentThread().name)
        binding.downloadBtn.setOnClickListener{
           // viewModel.setOneTimeWorkRequest1()
            viewModel.setNotifyOneTimeWorkRequest1()
        }
        binding.closeDimv.setOnClickListener{
            onBackPressed()
        }
    }
     fun askForPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@ManageDownloadActivity,
                    permission) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@ManageDownloadActivity,
                        permission)
            ) {
                ActivityCompat.requestPermissions(this@ManageDownloadActivity, arrayOf(permission),
                        requestCode)
            } else {
                ActivityCompat.requestPermissions(this@ManageDownloadActivity, arrayOf(permission),
                        requestCode)
            }
        } else if (ContextCompat.checkSelfPermission(this@ManageDownloadActivity,
                    permission) == PackageManager.PERMISSION_DENIED
        ) {
            Toast.makeText(applicationContext, "Permission was denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }
}