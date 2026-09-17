package com.example.capibara.presentation.main

import com.example.capibara.domain.model.DEFAULT_NOTIFY_BEFORE_MINUTES
import com.example.capibara.domain.model.Periodicity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ReminderFormUiState(
    val title: String = "",
    val date: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date()),
    val time: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
    val periodicity: String = Periodicity.DAILY,
    val notifyEnabled: Boolean = true,
    val notifyMinutes: String = DEFAULT_NOTIFY_BEFORE_MINUTES.toString(),
    val error: String? = null
)
