package com.jpndev.portfolio.ui.study.download

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.jpndev.portfolio.data.api.APIService
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.presentation.ui.servicestask.Constants
import com.jpndev.portfolio.presentation.ui.servicestask.Constants.Companion.cancelforeground
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import okhttp3.ResponseBody
import java.io.*

/*const val url_zip="https://github.com/anupamchugh/AnimateTextAndImageView/archive/master.zip"
const val url="http://sarintechy.xyz/frontend/web/uploads/Clouds_51630070986.mp4"
const val DIRECTORY="Portfolio"*/
@HiltWorker
class NotifyDownloadingWorker @AssistedInject constructor(@Assisted context: Context,@Assisted  params:WorkerParameters) : CoroutineWorker(context,params) {

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


            val progress = "Starting Download"
            setForeground(createForegroundInfo(progress))


            useCase.logsource.addLog("Notify WM D doWork Thread  : "+Thread.currentThread().name)
           loopfun()
           // downloadZipFile()
            val apiResult = useCase.executeDownloadRequest(url)
            apiResult.data?.let{
                isComplete= writeFn(it,videoFile)
            }


            useCase.logsource.addLog("Notify WM D Completed $isComplete")

        } catch (e: Exception) {
            useCase.logsource.addLog("Notify WM D Exception "+e.message)
           // return Result.failure()
        }
        finally {

            if (isComplete)
                return Result.success()
            else
                return Result.failure()
        }
    }


    lateinit var thread: Thread

    private fun loopfun() {
        thread= Thread(Runnable {
            for(i in 0..25) {
                // if(i%1000==0) {
                useCase.logsource.addLog("FS loopfun $i :" + Thread.currentThread().name)
                // }
                SystemClock.sleep(1000)
            }

        })
        thread.start()
    }

    private fun writeFn(body: ResponseBody,filename: String): Boolean {
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
            useCase.logsource.addLog("Notify WM D  writeFn : "+Thread.currentThread().name)

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
                        if(fileSizeDownloaded%10==0L) {
                            useCase.logsource.addLog("Notify WM D file download: " + 100 * fileSizeDownloaded.toFloat() / fileSize + " % Thread =" + Thread.currentThread().name)
                          //  useCase.logsource.addLog("Notify WM D file download: $fileSizeDownloaded of $fileSize" + " " + 100 * fileSizeDownloaded.toFloat() / fileSize + " % Thread =" + Thread.currentThread().name)

                        }
                    }
                    outputStream?.flush()
                    useCase.logsource.addLog("Notify WM D  completed : "+Thread.currentThread().name)
                    true
                }



            } catch (e: IOException) {
                useCase.logsource.addLog("Notify WM D file download: e= "+e.message)
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
            useCase.logsource.addLog("Notify WM D file download: outer e= "+e.message)
            false
        }
    }




    // Creates an instance of ForegroundInfo which can be used to update the
    // ongoing notification.
    private fun createForegroundInfo(progress: String): ForegroundInfo {

        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())


        val notification = NotificationCompat.Builder(applicationContext, Constants.channelID)
            .setContentTitle(Constants.foregroundServiceNotificationTitle)
            .setTicker(Constants.foregroundServiceNotificationTitle+" Ticker ")
            .setContentText(progress)
            .setSmallIcon(com.jpndev.portfolio.R.mipmap.ic_launcher)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancelforeground, intent)
            .build()

        return ForegroundInfo(1,notification)
    }



}