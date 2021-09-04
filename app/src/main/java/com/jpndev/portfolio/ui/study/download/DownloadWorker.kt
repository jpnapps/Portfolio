package com.jpndev.portfolio.ui.study.download

import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jpndev.portfolio.BuildConfig
import com.jpndev.portfolio.data.api.APIService
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.portfolio.domain.usecase.UseCase
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class DownloadWorker (context: Context,  params: WorkerParameters) : Worker(context,params) {



    lateinit var  apiservice: APIService


     val  logSource= LogSourceImpl()

lateinit var downloadZipFileTask:DownloadZipFileTask

    override fun doWork(): Result {
        try {
            //logSource.addLog("DW doWork Thread= "+Thread.currentThread().name)
            for (i in 1..900000000)
            {
                 if(i%1000000==0)
                  logSource.addLog("DW doWork ="+i+"  Thread= "+Thread.currentThread().name)
            }
           // downloadZipFile()


            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            Log.i("MYTAG", "Completed $currentDate")
            return Result.success()
        } catch (e: Exception) {

            //  useCase.logsource.addLog("DVM Exception "+e.message)
            Log.d(TAG, "DW Exception "+e.message)
            return Result.failure()
        }
    }


     fun downloadZipFile() {
        val okhttp= OkHttpClient. Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        apiservice= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttp)
            .build().create(APIService::class.java)


        logSource.addLog("DW downloadZipFile Thread= "+Thread.currentThread().name)
        //val url_zip="https://github.com/anupamchugh/AnimateTextAndImageView/archive/master.zip"
        val url="http://sarintechy.xyz/frontend/web/uploads/Clouds_51630070986.mp4"
        val call: Call<ResponseBody> = apiservice.downloadFileWithDynamicUrlSync(url)
        //  call.enqueue(Ca)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                logSource.addLog("DW Downloading..... Thread= "+Thread.currentThread().name)
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext, "Downloading...", Toast.LENGTH_SHORT).show()
                    downloadZipFileTask = DownloadZipFileTask()
                    downloadZipFileTask.setContexts(applicationContext)
                    downloadZipFileTask.setDownloadTask(downloadZipFileTask)
                    downloadZipFileTask.execute(response.body())
                }
                else {
                    Log.d(TAG, "Connection failed " + response.errorBody())
                    Toast.makeText(applicationContext, "Connection failed " + response.errorBody(), Toast.LENGTH_SHORT).show()
                }
            }



            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, t.message!!)
            }
        })

    }


     class DownloadZipFileTask : AsyncTask<ResponseBody, Pair<Int, Long>, String>()
    {
        lateinit var context:Context
        lateinit var downloadZipFileTask:DownloadZipFileTask
        override fun onPreExecute() {
            super.onPreExecute()
        }
         fun setContexts(temp:Context) {
             context=temp
        }
        fun setDownloadTask(temp:DownloadZipFileTask) {
            downloadZipFileTask=temp
        }
        override fun doInBackground(vararg urls: ResponseBody): String? { 
            //Copy you logic to calculate progress and call
           // saveToDisk(urls[0], "journaldev-project.zip")
            saveToDisk(urls[0], "clouds.mp4")
            return null
        }

        override fun onProgressUpdate(vararg progress: Pair<Int, Long>) {
            Log.d("API123", progress[0].second.toString() + " ")
            if (progress[0].first === 100)
                Toast.makeText(context,
                    "File downloaded successfully", Toast.LENGTH_SHORT).show()
            if (progress[0].second > 0) {
                val currentProgress =
                    (progress[0].first.toDouble() / progress[0].second.toDouble() * 100).toInt()
              //  progressBar.setProgress(currentProgress)
                //txtProgressPercent.setText("Progress $currentProgress%")
            }
            if (progress[0].first === -1) {
                Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
            }
        }

        fun doProgress(progressDetails: Pair<Int, Long>?) {
            publishProgress(progressDetails)
        }

        override fun onPostExecute(result: String?) {}

        fun saveToDisk(body: ResponseBody, filename: String) {
            try {
                val destinationFile =
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            filename)
                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null
                try {
                    inputStream = body.byteStream()
                    outputStream = FileOutputStream(destinationFile)
                    val data = ByteArray(4096)
                    var count: Int
                    var progress = 0
                    val fileSize = body.contentLength()
                    Log.d(TAG, "File Size=$fileSize")
                    while (inputStream.read(data).also { count = it } != -1) {
                        outputStream.write(data, 0, count)
                        progress += count
                        val pairs = Pair(progress, fileSize)
                        downloadZipFileTask.doProgress(pairs)
                        Log.d(TAG,
                                "Progress: " + progress + "/" + fileSize + " >>>> " + 100*progress.toFloat() / fileSize +"% Thread= "+Thread.currentThread().name)
                    }
                    outputStream.flush()
                    Log.d(TAG, destinationFile.parent)
                    val pairs = Pair(100, 100L)
                    downloadZipFileTask.doProgress(pairs)
                    return
                } catch (e: IOException) {
                    e.printStackTrace()
                    val pairs = Pair(-1, java.lang.Long.valueOf(-1))
                    downloadZipFileTask.doProgress(pairs)
                    Log.d(TAG, "Failed to save the file!")
                    return
                } finally {
                    inputStream?.close()
                    outputStream?.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d(TAG, "Failed to save the file!")
                return
            }
        }

    }



}