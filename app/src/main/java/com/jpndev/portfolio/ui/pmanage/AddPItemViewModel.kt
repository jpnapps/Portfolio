package  com.jpndev.portfolio.ui.pmanage

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import androidx.lifecycle.*


import com.google.gson.JsonObject
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class AddPItemViewModel(
    private val app: Application,
    private val usecase: UseCase

) : AndroidViewModel(app) {
    /*   @Inject
       lateinit var user: User
*/
    var action_menu_text: String = "Save"

    var isUpdate: Boolean = false

    // var pitem: PItem = PItem()
    //  get()=pitem
    var pitem_mld = MutableLiveData<PItem>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun copyTextFn(text: String) {
        var clipboardManager = app.getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager?
        val separator = "\n"
        // val text =pitem_mld.value?.value2
        var clipData = ClipData.newPlainText("  ", text)
        //   clipboardManager!!.primaryClip = clipData
        clipboardManager!!.setPrimaryClip(clipData)
        // jsontest=""
        ToastHandler.newInstance(app).mustShowToast("Copied Json data and cleared")
    }


    fun deletePItem(article: PItem) = viewModelScope.launch {
        usecase.executeDeletePItrm(article)
        pitem_mld.value?.let {

        } ?: let {

        }
    }


    fun checkExcute() = viewModelScope.launch {
        //    usecase.executeSavePItem(pitem)
        if (isUpdate)
            updatePItem()
        else
            savePItem()

    }

    fun savePItem() = viewModelScope.launch {
        //    usecase.executeSavePItem(pitem)
        usecase.logsource.addLog("Add before =" + pitem_mld.value?.value2)
        if (pitem_mld.value?.value2_encrypted ?: true)
            pitem_mld.value?.value2 = JAESUtils.encrypt(pitem_mld.value?.value2)
        if (pitem_mld.value?.value1_encrypted ?: true)
            pitem_mld.value?.value1 = JAESUtils.encrypt(pitem_mld.value?.value1)
        usecase.logsource.addLog("Add after= " + pitem_mld.value?.value2)
        val newRowId = usecase.executeSavePItem(pitem_mld.value!!)
        if (newRowId > -1) {
            statusMessage.value = Event("Added Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun updatePItem() = viewModelScope.launch {
        //    usecase.executeSavePItem(pitem)

        usecase.logsource.addLog("up before =" + pitem_mld.value?.value2)
        // pitem_mld.value?.value2= JAESUtils.encrypt( pitem_mld.value?.value2)
        if (pitem_mld.value?.value2_encrypted ?: true)
            pitem_mld.value?.value2 = JAESUtils.encrypt(pitem_mld.value?.value2)

        if (pitem_mld.value?.value1_encrypted ?: true)
            pitem_mld.value?.value1 = JAESUtils.encrypt(pitem_mld.value?.value1)
        usecase.logsource.addLog("up after= " + pitem_mld.value?.value2)
        val newRowId = usecase.executeUpdatePItem(pitem_mld.value!!)
        if (newRowId > -1) {
            statusMessage.value = Event("Updated Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getSavedPItems(): LiveData<List<PItem>> = liveData<List<PItem>> {
        mld_Progress.postValue(Resource.Loading())
        usecase.executeGetPList().collect {
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


