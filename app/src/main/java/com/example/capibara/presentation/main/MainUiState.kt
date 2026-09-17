package com.example.capibara.presentation.main

import com.example.capibara.domain.model.PetStats
import com.example.capibara.domain.model.Reminder

data class MainUiState(
    val date: String = "",
    val isToday: Boolean = true,
    val petStats: PetStats? = null,
    val reminders: List<Reminder> = emptyList(),
    val allReminders: List<Reminder> = emptyList(),
    val selectedTab: Int = MainTab.HOME,
    val isFormOpen: Boolean = false
)

object MainTab {
    const val GAMES = 0
    const val SHOP = 1
    const val HOME = 2
    const val DOCTOR = 3
}
