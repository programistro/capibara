package com.example.capibara.presentation.main

import com.example.capibara.domain.model.Periodicity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ReminderFormUiState(
    val title: String = "",
    val date: String = SimpleDateFormat("d.M.yyyy", Locale.getDefault()).format(Date()),
    val time: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
    val periodicity: String = Periodicity.DAILY,
    val error: String? = null
)
