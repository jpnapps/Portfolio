package com.beeone.techbank.sign.kyc.sumsub
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*


import com.google.gson.JsonObject
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class AddPItemViewModel (
    private val app: Application,
    private val usecase: UseCase

    ) : AndroidViewModel(app)
    {
     /*   @Inject
        lateinit var user: User
*/

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


