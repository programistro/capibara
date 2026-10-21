package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.repository.ReminderRepository
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    operator fun invoke(date: String): List<Reminder> = repository.getReminders(date)
}
