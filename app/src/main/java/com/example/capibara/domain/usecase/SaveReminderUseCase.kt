package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.repository.ReminderRepository
import com.example.capibara.domain.scheduler.ReminderScheduler
import javax.inject.Inject

class SaveReminderUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val scheduler: ReminderScheduler
) {
    operator fun invoke(
        title: String,
        date: String,
        time: String,
        periodicity: String,
        notifyBeforeMinutes: Int,
        withNotification: Boolean
    ): SaveResult? {
        if (title.isBlank() || date.isBlank() || time.isBlank()) return null
        val reminder = Reminder(
            title = title.trim(),
            date = date,
            time = time,
            periodicity = periodicity,
            notifyBeforeMinutes = notifyBeforeMinutes.coerceAtLeast(0)
        )
        val id = repository.saveReminder(reminder)
        var triggerAt: Long? = null
        if (withNotification) {
            val saved = reminder.copy(id = id)
            scheduler.schedule(saved)
            triggerAt = scheduler.nextTriggerMillis(saved)
        }
        return SaveResult(id = id, triggerAtMillis = triggerAt)
    }
}

data class SaveResult(
    val id: Long,
    val triggerAtMillis: Long?
)
