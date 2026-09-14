package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.repository.ReminderRepository
import com.example.capibara.domain.scheduler.ReminderScheduler
import javax.inject.Inject

class SaveReminderUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val scheduler: ReminderScheduler
) {
    operator fun invoke(title: String, date: String, time: String, periodicity: String): Long? {
        if (title.isBlank() || date.isBlank() || time.isBlank()) return null
        val id = repository.saveReminder(
            Reminder(title = title.trim(), date = date, time = time, periodicity = periodicity)
        )
        val saved = Reminder(id = id, title = title.trim(), date = date, time = time, periodicity = periodicity)
        scheduler.schedule(saved)
        return id
    }
}
