package com.example.capibara.presentation.main

import com.example.capibara.domain.model.Article
import com.example.capibara.domain.model.PetStats
import com.example.capibara.domain.model.Reminder

data class MainUiState(
    val date: String = "",
    val isToday: Boolean = true,
    val petStats: PetStats? = null,
    val reminders: List<Reminder> = emptyList(),
    val news: List<Article> = emptyList(),
    val selectedTab: MainTab,
    val isFormOpen: Boolean = false,
    val selectedItemId: Int? = null,
    val isRefreshing: Boolean = false,
    val newsError: String? = null
)