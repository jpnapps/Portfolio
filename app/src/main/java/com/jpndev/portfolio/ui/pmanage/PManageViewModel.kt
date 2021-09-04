package com.jpndev.portfolio.ui.pmanage
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*


import com.google.gson.JsonObject
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.ui.study.actvity.LifeCycleActivity
import com.jpndev.portfolio.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class PManageViewModel (
    private val app: Application,
    private val usecase: UseCase

    ) : AndroidViewModel(app)
    {
     /*   @Inject
        lateinit var user: User
*/

        public fun getUseCase() =usecase

        var text = MutableLiveData<String>()


        init {
            LogUtils.LOGD("pref_lc","\n PManageViewModel = "+ usecase.prefUtils.getString("lifecycle","Nothing found"))
            text.value =   usecase.prefUtils.getString("lifecycle","Nothing found")
        }


        fun deletePItem(article: PItem) =viewModelScope.launch {
            usecase.executeDeletePItrm(article)

        }


        fun savePItem(article: PItem) =viewModelScope.launch {
            usecase.executeSavePItem(article)
        }

        fun getSavedPItems(): LiveData<List<PItem>> = liveData<List<PItem>>{
            mld_Progress.postValue(Resource.Loading())

            usecase.executeGetPList().collect{
                emit(it)
            }



        }


        fun showAddPItemActivity(activity: Activity) =viewModelScope.launch {
            val intent = Intent(activity, AddPItemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }
        fun showLifeCycleActivity(activity: Activity) =viewModelScope.launch {
            val intent = Intent(activity, LifeCycleActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }



        val mld_Progress: MutableLiveData<Resource<PItem>> = MutableLiveData()







        private fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false


        }
    }


