package com.example.capibara.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.domain.usecase.RegisterMoodChangeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReminderAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(EXTRA_ID, 0L)
        val title = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        val taken = intent.getBooleanExtra(EXTRA_TAKEN, false)
        if (id == -0L || title.isEmpty()) return
        val periodicity = intent.getStringExtra(EXTRA_PERIODICITY)
            .orEmpty()
            .ifEmpty { Periodicity.DAILY }

        Log.d(TAG, "onReceive: сработал аларм id=$id title=$title")

        if(intent.action == "ACTION_REMINDER_ACCEPTED"){
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(id.toInt())
        }

        ReminderNotificationHelper.showReminder(context, id, title)

        // Планируем следующий приём строго через один период от текущего срабатывания,
        // иначе напоминание «уплывает» и перестаёт совпадать с выбранным временем.
        val triggerAt = System.currentTimeMillis() + Periodicity.intervalMillis(periodicity)
        AlarmReminderScheduler(context.applicationContext)
            .scheduleAt(id, title, periodicity, triggerAt)
    }

    companion object {
        const val EXTRA_ID = "reminder_id"
        const val EXTRA_TITLE = "reminder_title"
        const val EXTRA_PERIODICITY = "reminder_periodicity"
        const val EXTRA_TAKEN = "action_taken"
        private const val TAG = "ReminderAlarm"
    }
}
