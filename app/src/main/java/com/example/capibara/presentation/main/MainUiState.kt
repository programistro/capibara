package com.example.capibara.presentation.main

import com.example.capibara.domain.model.PetStats
import com.example.capibara.domain.model.Reminder

data class MainUiState(
    val date: String = "",
    val isToday: Boolean = true,
    val petStats: PetStats? = null,
    val reminders: List<Reminder> = emptyList(),
    val allReminders: List<Reminder> = emptyList(),
    val selectedTab: MainTab,
    val isFormOpen: Boolean = false
)