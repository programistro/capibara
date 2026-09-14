package com.example.capibara.domain.repository

import com.example.capibara.domain.model.Reminder

interface ReminderRepository {
    fun getReminders(date: String): List<Reminder>
    fun saveReminder(reminder: Reminder): Long
}
