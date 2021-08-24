package  com.jpndev.portfolio.ui.study.actvity
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
import com.jpndev.portfolio.ui.pmanage.AddPItemActivity
import com.jpndev.portfolio.ui.pmanage.PManageActivity
import com.jpndev.portfolio.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class LifeCycleViewModel (
    private val app: Application,
    private val usecase: UseCase

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

        var run_text = MutableLiveData<String>()
        init {
            text.value = "init"
        }
        private val statusMessage = MutableLiveData<Event<String>>()
        val message: LiveData<Event<String>>
            get() = statusMessage



        fun showPManageActivity(activity: Activity) =viewModelScope.launch {
            val intent = Intent(activity, PManageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }
        fun showLifeCycleActivity(activity: Activity) =viewModelScope.launch {
            val intent = Intent(activity, LifeCycleActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun addLog(text: String,tag:String="jp") =viewModelScope.launch {
            usecase.logsource.addLog(text,tag)

        }


    }


