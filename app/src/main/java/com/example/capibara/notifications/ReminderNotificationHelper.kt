package com.example.capibara.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.capibara.MainActivity
import com.example.capibara.R

object ReminderNotificationHelper {

    const val CHANNEL_ID = "pill_reminders"
    private const val TAG = "ReminderAlarm"

    fun ensureChannel(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (manager.getNotificationChannel(CHANNEL_ID) == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Напоминания о лекарствах",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
    }

    fun showReminder(context: Context, reminderId: Long, title: String) {
        ensureChannel(context)
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("reminder_id", reminderId)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val acceptIntent = Intent(context, ReminderActionReceiver::class.java).apply {
            action = ReminderActionReceiver.ACTION_ACCEPTED
            putExtra("reminder_id", reminderId)
            putExtra("action_taken", true)
        }
        val acceptPendingIntent = PendingIntent.getBroadcast(
            context,
            (reminderId * 2).toInt(), // Можно использовать тот же ID или другой уникальный
            acceptIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val missIntent = Intent(context, ReminderActionReceiver::class.java).apply {
            action = ReminderActionReceiver.ACTION_MISSED
            putExtra("reminder_id", reminderId)
            putExtra("action_taken", false)
        }
        val missPendingIntent = PendingIntent.getBroadcast(
            context,
            (reminderId * 2 + 1).toInt(), // Можно использовать тот же ID или другой уникальный
            missIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_coin)
            .setContentTitle("Время принять лекарство")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(0, "Принял", acceptPendingIntent)
            .addAction(0, "Пропустил", missPendingIntent)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(reminderId.toInt(), notification)
        Log.d(TAG, "showReminder: пуш показан id=$reminderId title=$title")
    }
}
