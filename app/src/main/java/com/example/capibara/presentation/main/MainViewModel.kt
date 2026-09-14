package com.example.capibara.presentation.main

import androidx.lifecycle.ViewModel
import com.example.capibara.domain.usecase.GetPetStatsUseCase
import com.example.capibara.domain.usecase.GetRemindersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPetStats: GetPetStatsUseCase,
    private val getReminders: GetRemindersUseCase
) : ViewModel() {

    private val today: String =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

    private val _uiState = MutableStateFlow(
        MainUiState(
            date = today,
            petStats = getPetStats(),
            reminders = getReminders(today)
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index, isFormOpen = false) }
    }

    fun onOpenForm() {
        _uiState.update { it.copy(isFormOpen = true) }
    }

    fun onCloseForm() {
        _uiState.update { it.copy(isFormOpen = false) }
        refreshReminders()
    }

    fun onTodayClick() {
        refreshReminders()
    }

    private fun refreshReminders() {
        _uiState.update { it.copy(reminders = getReminders(today)) }
    }
}
