package com.example.capibara.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.domain.usecase.RegisterMoodChangeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderActionReceiver : BroadcastReceiver() {
    @Inject lateinit var registerMood: RegisterMoodChangeUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(EXTRA_ID, 0L)
        val taken = intent.getBooleanExtra(EXTRA_TAKEN, false)
        if (id == 0L) return

        Log.d(TAG, "onReceive: действие id=$id taken=$taken")

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                registerMood(taken = taken)
                NotificationManagerCompat.from(context).cancel(id.toInt())
            } catch (e: Exception) {
                Log.e(TAG, "не удалось обработать действие", e)
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val EXTRA_ID = "reminder_id"
        const val EXTRA_TITLE = "reminder_title"
        const val EXTRA_PERIODICITY = "reminder_periodicity"
        const val EXTRA_TAKEN = "action_taken"
        private const val TAG = "ReminderAlarm"
        const val ACTION_ACCEPTED = "com.example.capibara.action.REMINDER_ACCEPTED"
        const val ACTION_MISSED = "com.example.capibara.action.REMINDER_MISSED"
    }
}
