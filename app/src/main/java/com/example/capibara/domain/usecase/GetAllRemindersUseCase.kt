package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.repository.ReminderRepository
import javax.inject.Inject

class GetAllRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    operator fun invoke(): List<Reminder> = repository.getAllReminders()
}
