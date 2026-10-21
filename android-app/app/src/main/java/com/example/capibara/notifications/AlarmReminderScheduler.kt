package com.example.capibara.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.scheduler.ReminderScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AlarmReminderScheduler @Inject constructor(
    @param: ApplicationContext private val context: Context
) : ReminderScheduler {

    override fun schedule(reminder: Reminder) {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            Log.w(TAG, "schedule(id=${reminder.id}): уведомления выключены в системе, пуш не покажется!")
        }
        val triggerAt = nextTriggerMillis(reminder)
        if (triggerAt == null) {
            Log.w(TAG, "schedule(id=${reminder.id}): не смог посчитать время (date=${reminder.date}, time=${reminder.time})")
            return
        }
        Log.d(TAG, "schedule(id=${reminder.id}): аларм на ${format(triggerAt)}")
        scheduleAt(reminder, triggerAt)
    }

    override fun nextTriggerMillis(reminder: Reminder): Long? =
        nextTriggerMillis(reminder, System.currentTimeMillis())

    fun scheduleAt(reminder: Reminder, triggerAt: Long) =
        scheduleAt(reminder.id, reminder.title, reminder.periodicity, triggerAt)

    fun scheduleAt(id: Long, title: String, periodicity: String, triggerAt: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra(ReminderAlarmReceiver.EXTRA_ID, id)
            putExtra(ReminderAlarmReceiver.EXTRA_TITLE, title)
            putExtra(ReminderAlarmReceiver.EXTRA_PERIODICITY, periodicity)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
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
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra(ReminderAlarmReceiver.EXTRA_ID, reminderId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private const val TAG = "ReminderAlarm"
        private const val DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm"

        private fun format(millis: Long): String =
            SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault()).format(Date(millis))

        fun nextTriggerMillis(reminder: Reminder, now: Long = System.currentTimeMillis()): Long? {
            val takesAt = parseTakesAt(reminder) ?: return null
            val interval = Periodicity.intervalMillis(reminder.periodicity)
            val notifyBefore = reminder.notifyBeforeMinutes * 60_000L

            // Время самого приёма: если оно уже прошло — переносим по периодичности.
            var takeTime = takesAt
            if (takeTime <= now) {
                val missed = (now - takeTime) / interval + 1
                takeTime += missed * interval
            }

            // Напоминание за N минут до приёма; если эта точка уже в прошлом —
            // показываем сразу, а не откладываем на следующий период.
            val trigger = takeTime - notifyBefore
            return if (trigger <= now) now + 1_000L else trigger
        }

        fun parseTakesAt(reminder: Reminder): Long? = try {
            SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
                .parse("${reminder.date} ${reminder.time}")?.time
        } catch (_: Exception) {
            null
        }
    }
}
