package com.example.capibara.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.scheduler.ReminderScheduler
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class AlarmReminderScheduler @Inject constructor(
    private val context: Context
) : ReminderScheduler {

    override fun schedule(reminder: Reminder) {
        val triggerAt = nextTriggerMillis(reminder) ?: return
        scheduleAt(reminder, triggerAt)
    }

    fun scheduleAt(reminder: Reminder, triggerAt: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra(ReminderAlarmReceiver.EXTRA_ID, reminder.id)
            putExtra(ReminderAlarmReceiver.EXTRA_TITLE, reminder.title)
            putExtra(ReminderAlarmReceiver.EXTRA_PERIODICITY, reminder.periodicity)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    pendingIntent
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAt,
                pendingIntent
            )
        }
    }

    override fun cancel(reminderId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private const val DATE_TIME_PATTERN = "d.M.yyyy HH:mm"

        fun nextTriggerMillis(reminder: Reminder, now: Long = System.currentTimeMillis()): Long? {
            val first = try {
                SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
                    .parse("${reminder.date} ${reminder.time}")?.time
            } catch (_: Exception) {
                null
            } ?: return null
            val interval = Periodicity.intervalMillis(reminder.periodicity)
            var trigger = first
            while (trigger <= now) trigger += interval
            return trigger
        }
    }
}
