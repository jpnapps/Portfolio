package  com.jpndev.portfolio.ui.study.download
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*


import com.google.gson.JsonObject
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.ui.pmanage.AddPItemActivity
import com.jpndev.portfolio.ui.pmanage.PManageActivity
import com.jpndev.portfolio.ui.study.actvity.LifeCycleActivity
import com.jpndev.portfolio.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val TAG="jp"
const val DOWNLOAD = "download"
class DownloadViewModel (
    private val app: Application,
    public val usecase: UseCase

    ) : AndroidViewModel(app)
    {
     /*   @Inject
        lateinit var user: User
*/
        var heading:String="INIT"

        var isUpdate:Boolean=false

       // var text:String="var init"
          //  get()=pitem

       // var text:LiveData<String>="var init"

        var text = MutableLiveData<String>()


        init {
            usecase.logsource.addLog("init DownloadViewModel ")

            val separator = "\n"

            text.value  ="http://sarintechy.xyz/frontend/web/uploads/Clouds_51630070986.mp4"

        }
        private val statusMessage = MutableLiveData<Event<String>>()
        val message: LiveData<Event<String>>
            get() = statusMessage

//http://sarintechy.xyz/frontend/web/uploads/Clouds_51630070986.mp4


        fun setOneTimeWorkRequest1()  =viewModelScope.launch(Dispatchers.IO) {
            usecase.logsource.addLog(
                    "DVM setOneTimeWorkRequest1 thread = " + Thread.currentThread())
            val workManager = WorkManager.getInstance(app)
            val data: Data = Data.Builder().putString(DOWNLOAD, "" + text.value)

                .build()
            val constraints = Constraints.Builder().setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)

                .build()

            val downloadRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
                .setConstraints(constraints).setInputData(data).build()

            workManager.enqueue(downloadRequest);
        }

         fun setOneTimeWorkRequest2() {

            val workManager = WorkManager.getInstance(app)
            val data: Data = Data.Builder()
                .putString(DOWNLOAD,""+ text.value )
                .build()
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)

                .build()

            val downloadRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

             val blurBuilder = OneTimeWorkRequestBuilder<DownloadWorker>()

             usecase.logsource.addLog("DVM setOneTimeWorkRequest2 Thread= "+Thread.currentThread().name)
             workManager.enqueue(downloadRequest);
        }



        fun showPManageActivity(activity: Activity) =viewModelScope.launch {




            /*val downloadService: FileDownloadService =
                ServiceGenerator.create(FileDownloadService::class.java)

            val call: Call<ResponseBody> = downloadService.downloadFileWithDynamicUrlSync(fileUrl)

            call.enqueue(object : Callback<ResponseBody?>() {
                override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                    if (response.isSuccess()) {
                        Log.d(TAG, "server contacted and has file")
                        val writtenToDisk: Boolean = writeResponseBodyToDisk(response.body())
                        Log.d(TAG, "file download was a success? $writtenToDisk")
                    } else {
                        Log.d(TAG, "server contact failed")
                    }
                }

                fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                    Log.e(TAG, "error")
                }
            })*/
        }
        fun showLifeCycleActivity(activity: Activity) =viewModelScope.launch {
            val intent = Intent(activity, LifeCycleActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

    }


