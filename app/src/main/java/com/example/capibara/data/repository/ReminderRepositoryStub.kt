package com.example.capibara.data.repository

import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.repository.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryStub @Inject constructor() : ReminderRepository {
    private val reminders = mutableListOf<Reminder>()
    private var nextId = 1L

    override fun getReminders(date: String): List<Reminder> =
        reminders.filter { it.date == date }

    override fun saveReminder(reminder: Reminder): Long {
        val id = nextId++
        reminders.add(reminder.copy(id = id))
        return id
    }
}
