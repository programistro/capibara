package com.example.capibara.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.domain.model.Reminder

class ReminderAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(EXTRA_ID, 0L)
        val title = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        val periodicity = intent.getStringExtra(EXTRA_PERIODICITY)
            .orEmpty()
            .ifEmpty { Periodicity.DAILY }
        Log.d(TAG, "onReceive: сработал аларм id=$id title=$title")
        if (id == 0L || title.isEmpty()) return

        ReminderNotificationHelper.showReminder(context, id, title)

        val scheduler = AlarmReminderScheduler(context.applicationContext)
        val next = System.currentTimeMillis() + Periodicity.intervalMillis(periodicity)
        scheduler.scheduleAt(
            Reminder(id = id, title = title, date = "", time = "", periodicity = periodicity),
            next
        )
    }

    companion object {
        const val EXTRA_ID = "reminder_id"
        const val EXTRA_TITLE = "reminder_title"
        const val EXTRA_PERIODICITY = "reminder_periodicity"
        private const val TAG = "ReminderAlarm"
    }
}
