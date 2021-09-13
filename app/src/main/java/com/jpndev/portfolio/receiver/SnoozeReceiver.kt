/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jpndev.portfolio.receiver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.text.format.DateUtils
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import com.jpndev.portfolio.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.portfolio.ui.pmanage.AddPItemViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SnoozeReceiver: BroadcastReceiver() {
    private val REQUEST_CODE = 0
    @Inject
    lateinit var  logSourceImpl: LogSourceImpl

    override fun onReceive(context: Context, intent: Intent) {
        val triggerTime = SystemClock.elapsedRealtime() + (DateUtils.MINUTE_IN_MILLIS/6)
        logSourceImpl.addLog("SR onReceive")
        val notifyIntent = Intent(context, AlarmReceiver::class.java)
        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        logSourceImpl.addLog("SR onReceive MINUTE_IN_MILLIS = "+DateUtils.MINUTE_IN_MILLIS)
        logSourceImpl.addLog("SR onReceive  SystemClock.elapsedRealtime() = "+ SystemClock.elapsedRealtime())
        logSourceImpl.addLog("SR onReceive triggerTime = "+triggerTime)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            notifyPendingIntent
        )

        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }

}