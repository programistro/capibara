package com.example.capibara.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capibara.domain.repository.WalletRepository
import com.example.capibara.domain.usecase.GetAllRemindersUseCase
import com.example.capibara.domain.usecase.GetPetStatsUseCase
import com.example.capibara.domain.usecase.GetRemindersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPetStats: GetPetStatsUseCase,
    private val getReminders: GetRemindersUseCase,
    private val getAllReminders: GetAllRemindersUseCase,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val today: String =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

    private val _uiState = MutableStateFlow(
        MainUiState(
            date = today,
            petStats = getPetStats(),
            reminders = getReminders(today),
            allReminders = getAllReminders()
        )
    )

    init {
        viewModelScope.launch {
            walletRepository.observeCoins().collect { coins ->
                _uiState.update { it.copy(petStats = it.petStats?.copy(coins = coins)) }
            }
        }
    }

    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index, isFormOpen = false) }
    }

    fun onOpenForm() {
        _uiState.update { it.copy(isFormOpen = true) }
    }

    fun onCloseForm() {
        _uiState.update { it.copy(isFormOpen = false) }
        refreshReminders(_uiState.value.date)
        refreshAllReminders()
    }

    fun onDateSelected(date: String) {
        _uiState.update { it.copy(date = date, isToday = date == today) }
        refreshReminders(date)
    }

    fun onTodayClick() {
        _uiState.update { it.copy(date = today, isToday = true) }
        refreshReminders(today)
    }

    private fun refreshReminders(date: String) {
        _uiState.update { it.copy(reminders = getReminders(date)) }
    }

    private fun refreshAllReminders() {
        _uiState.update { it.copy(allReminders = getAllReminders()) }
    }
}
