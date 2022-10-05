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
import com.jpndev.portfolio.utils.LogUtils
import com.jpndev.portfolio.utils.parser.PDataParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import java.util.concurrent.Executor

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



        private lateinit var executor: Executor
        private lateinit var biometricPrompt: BiometricPrompt
        public fun setBioPrompt(activity: FragmentActivity, lmbd: () -> Unit) {
            executor = ContextCompat.getMainExecutor(activity)
            biometricPrompt = BiometricPrompt(activity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(app,
                            "Authentication error: $errString", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(app,
                            "Authentication succeeded!", Toast.LENGTH_SHORT)
                            .show()
                        lmbd()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(app, "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show()

                        // finish()
                    }
                })
        }

        public fun setBioAuth(activity: FragmentActivity, lmbd: () -> Unit) {
            // Allows user to authenticate using either a Class 3 biometric or
// their lock screen credential (PIN, pattern, or password).
            setBioPrompt(activity,lmbd)
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
         * function used to get watch settings item data from configuration file
         */
        private fun getPDatasFromAssets() {
            // array list for watch settings
            pdatajsonList = ArrayList()
            // method to get the data from watch settings configuration file
            pdatajsonList = PDataParser.parse(app.applicationContext)
        }

    }


