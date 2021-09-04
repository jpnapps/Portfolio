package com.jpndev.portfolio

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var   logSource: LogSourceImpl

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onTerminate() {
        super.onTerminate()
        logSource.addLog("app onTerminate   Thread= "+Thread.currentThread().name)
    }

    suspend open fun addLog(obj: Any?) {

   /*     coroutineScope {*/

            try {


                if (obj is String) {

                    logSource.addLog("\n Text  = " +obj)


                }



            } catch (e: Exception) {
                // activity.hideProgress()
            }


        }
}
