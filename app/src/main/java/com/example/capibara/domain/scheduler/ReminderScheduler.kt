package com.example.capibara.domain.scheduler

import com.example.capibara.domain.model.Reminder

interface ReminderScheduler {
    fun schedule(reminder: Reminder)
    fun cancel(reminderId: Long)
    fun nextTriggerMillis(reminder: Reminder): Long?
}
