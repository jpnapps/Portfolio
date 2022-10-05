package com.jpndev.portfolio.ui.pmanage

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*


import com.google.gson.JsonObject
import com.jpndev.portfolio.data.model.PInputData
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.domain.usecase.UseCase
import com.jpndev.portfolio.ui.study.actvity.LifeCycleActivity
import com.jpndev.portfolio.utils.AESUtils
import com.jpndev.portfolio.utils.Event
import com.jpndev.portfolio.utils.JAESUtils
import com.jpndev.portfolio.utils.LogUtils
import com.jpndev.portfolio.utils.parser.PDataParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import java.util.concurrent.Executor

class PManageViewModel(
    private val app: Application,
    private val usecase: UseCase

) : AndroidViewModel(app) {
    /*   @Inject
       lateinit var user: User
*/

    fun getUseCase() = usecase

    var text = MutableLiveData<String>()


    init {
        LogUtils.LOGD(
            "pref_lc",
            "\n PManageViewModel = " + usecase.prefUtils.getString("lifecycle", "Nothing found")
        )
        text.value = usecase.prefUtils.getString("lifecycle", "Nothing found")
    }

    fun deletePItem(article: PItem) = viewModelScope.launch {
        usecase.executeDeletePItrm(article)
    }

    fun savePItem(article: PItem) = viewModelScope.launch {
        usecase.executeSavePItem(article)
    }

    fun getSavedPItems(): LiveData<List<PItem>> = liveData<List<PItem>> {
        mld_Progress.postValue(Resource.Loading())
        usecase.executeGetPList().collect {
            emit(it)
        }
    }


    fun showAddPItemActivity(activity: Activity) = viewModelScope.launch {
        val intent = Intent(activity, AddPItemActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity?.startActivity(intent)
    }

    fun showLifeCycleActivity(activity: Activity) = viewModelScope.launch {
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


    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    public fun setBioPrompt(activity: FragmentActivity, lmbd: () -> Unit) {
        executor = ContextCompat.getMainExecutor(activity)
        biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        app,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        app,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    lmbd()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        app, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    // finish()
                }
            })
    }

    public fun setBioAuth(activity: FragmentActivity, lmbd: () -> Unit) {
        // Allows user to authenticate using either a Class 3 biometric or
// their lock screen credential (PIN, pattern, or password).
        setBioPrompt(activity, lmbd)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            // .setNegativeButtonText("Use account password")
            .setDeviceCredentialAllowed(true)
            .build()
        // Can't call setNegativeButtonText() and
        // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
        // .setNegativeButtonText("Use account password")

        biometricPrompt.authenticate(promptInfo)
    }

    // array list for p input data from json
    private lateinit var pdatajsonList: ArrayList<PInputData>

    /**
     * function used to get p data from json file
     */
    fun getPDatasFromAssets() {
        // array list for p input data
        pdatajsonList = ArrayList()
        // method to get p data from json file
        pdatajsonList = PDataParser.parse(app.applicationContext)
        usecase.logsource.addLog("PMV Parse pdatajsonList size " + pdatajsonList.size)
        for (i in 0..pdatajsonList.size - 1) {
            savePItemfromJson(pInputData = pdatajsonList.get(i), i)
        }
    }

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    /**
     * function used to save p item from Json
     */
    fun savePItemfromJson(pInputData: PInputData, pos: Int) = viewModelScope.launch {
        val pItem = convertToPItem(pInputData)
        usecase.logsource.addLog("Add before =" + pItem.value2)
        pItem.value2 = JAESUtils.encrypt(decryptJp(pItem.value2))
        usecase.logsource.addLog("Add after= " + pItem.value2)
        val newRowId = usecase.executeSavePItem(pItem)
        if (newRowId > -1) {
            statusMessage.value = Event("Added " + pos + " Successfully $newRowId")
            usecase.logsource.addLog("Added " + pos + " Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error " + pos + " Occurred")
            usecase.logsource.addLog("Error " + pos + "Occurred")
        }
    }

    /**
     * function used to convertToPItem
     */
    private fun convertToPItem(pInputData: PInputData): PItem {
        return PItem().apply {
            key1 = pInputData.key_1
            value1 = pInputData.value_1
            key2 = pInputData.key_2
            value2 = pInputData.value_2
            value1_encrypted = false
            value2_encrypted = true
        }
    }

    /**
     * function used to getValueString
     */
    fun getValueString(value1Encrypted: Boolean, value1: String, temp: String): String {
        //   var text=""
        return if (value1Encrypted) {
            encryptJp(AESUtils.decrypt(value1) ?: temp)
        } else value1

    }

    /**
     * function used to encryptJp
     */
    fun encryptJp(text: String): String {
        var text_enc = text
        text_enc = text.replace("rajpal", "hashvk", true)
        text_enc = text.replace("Rajpal", "hashMsd", true)
        text_enc = text_enc.replace("#", "@", true)
        return text_enc
    }

    /**
     * function used to decryptJp
     */
    fun decryptJp(text: String): String {
        var text_enc = text
        text_enc = text.replace("hashvk", "rajpal", true)
        text_enc = text_enc.replace("@", "#", true)
        return text_enc

    }
}


