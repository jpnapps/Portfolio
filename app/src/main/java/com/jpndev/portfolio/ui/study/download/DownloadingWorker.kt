package com.jpndev.portfolio.ui.study.download

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


import android.widget.Toast
import androidx.work.CoroutineWorker
import com.jpndev.portfolio.data.api.APIService
import com.jpndev.portfolio.domain.usecase.UseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import okhttp3.ResponseBody
import java.io.*

const val url_zip="https://github.com/anupamchugh/AnimateTextAndImageView/archive/master.zip"
const val url="http://sarintechy.xyz/frontend/web/uploads/Clouds_51630070986.mp4"
const val DIRECTORY="Portfolio"
@HiltWorker
class DownloadingWorker @AssistedInject constructor(@Assisted context: Context,@Assisted  params:WorkerParameters) : CoroutineWorker(context,params) {

    @Inject
    lateinit var  apiservice: APIService

    @Inject
    lateinit var  useCase: UseCase

    var  temp_context: Context=context


    val videoFile:String
          get()="Video_"+System.currentTimeMillis()+".mp4"


    override suspend fun doWork(): Result {
        var isComplete=false
        try {

            useCase.logsource.addLog("WM D doWork Thread ="+Thread.currentThread().name)
           // downloadZipFile()
            val apiResult = useCase.executeDownloadRequest(url)
            apiResult.data?.let{
                isComplete= writeResponseBodyToDisk(it,videoFile)
            }


            useCase.logsource.addLog("WM D Completed $isComplete")

        } catch (e: Exception) {
            useCase.logsource.addLog("WM D Exception "+e.message)
           // return Result.failure()
        }
        finally {

            if (isComplete)
                return Result.success()
            else
                return Result.failure()
        }
    }
    /*  private var networkComponent: NetworkComponent? = null
    private fun getNetworkService(context: Context): RandomUsersApi? {
        if (networkComponent == null) {
            networkComponent =
                DaggerNetworkComponent.builder().contextModule(ContextModule(context))
                    .networkModule(NetworkModule()).okHttpClientModule(OkHttpClientModule()).build()
        }
        return networkComponent.getService()
    }*/




    fun funZ() {
        val relativePath =
            Environment.DIRECTORY_DOCUMENTS + File.separator + DIRECTORY // save directory
        val fileName = videoFile // file name to save file with
        val mimeType = "video/*" // Mime Types define here
        val bitmap: Bitmap? = null // your bitmap file to save
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        val resolver: ContentResolver = temp_context.getContentResolver()
        var stream: OutputStream? = null
        var uri: Uri? = null
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, contentValues)
            if (uri == null) {
                Log.d("error", "Failed to create new  MediaStore record.")
                return
            }
            stream = resolver.openOutputStream(uri)
            if (stream == null) {
                Log.d("error", "Failed to get output stream.")
            }
            val saved = bitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            if (!saved) {
                Log.d("error", "Failed to save bitmap.")
            }
        } catch (e: IOException) {
            if (uri != null) {
                resolver.delete(uri, null, null)
            }
        } finally {
            stream?.close()
        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody,filename: String): Boolean {
        return try {

            val relativePath =
                Environment.DIRECTORY_MOVIES + File.separator + DIRECTORY // save directory
            val fileName = videoFile // file name to save file with
            val mimeType = "video/*" // Mime Types define here
            val bitmap: Bitmap? = null // your bitmap file to save
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
            val resolver: ContentResolver = temp_context.getContentResolver()

     /*       val destinationFile =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        filename)*/
            useCase.logsource.addLog("WM D  writeResponseBodyToDisk  thread = "+Thread.currentThread())

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            var uri: Uri? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()

                val contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                uri = resolver.insert(contentUri, contentValues)
                if (uri == null) {
                    Log.d("error", "Failed to create new  MediaStore record.")
                    false
                }
                else {
                    outputStream = resolver.openOutputStream(uri)
                    if (outputStream == null) {
                        Log.d("error", "Failed to get output stream.")
                        false
                    }


                    // outputStream = FileOutputStream(destinationFile)
                    while (true) {
                        val read: Int = inputStream.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        outputStream?.write(fileReader, 0, read)
                        fileSizeDownloaded += read.toLong()
                        useCase.logsource.addLog(
                                "WM D file download: $fileSizeDownloaded of $fileSize" + " " + 100 * fileSizeDownloaded.toFloat() / fileSize + " % Thread =" + Thread.currentThread().name)
                    }
                    outputStream?.flush()
                    true
                }
            } catch (e: IOException) {
                useCase.logsource.addLog("WM D file download: e= "+e.message)
                false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            useCase.logsource.addLog("WM D file download: outer e= "+e.message)
            false
        }
    }



    private fun downloadZipFile() {
        useCase.logsource.addLog("WM D  downloadZipFile Thread="+Thread.currentThread())

        val call: Call<ResponseBody> = apiservice.downloadFileWithDynamicUrlSync(url_zip)
        //  call.enqueue(Ca)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful()) {
                    useCase.logsource.addLog("WM D  Downloading... thread = "+Thread.currentThread())
                    response.body()?.let {   writeResponseBodyToDisk(it,"master.zip") }

                    Toast.makeText(applicationContext, "Downloading...", Toast.LENGTH_SHORT).show()
                }
                else {

                    useCase.logsource.addLog("WM D  Connection failed ... "+  response.errorBody())
                    Toast.makeText(applicationContext, "Connection failed " + response.errorBody(), Toast.LENGTH_SHORT).show()
                }
            }



            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, t.message!!)
            }
        })

    }
}