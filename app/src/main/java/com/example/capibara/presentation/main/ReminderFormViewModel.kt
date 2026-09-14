package com.example.capibara.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capibara.domain.usecase.SaveReminderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReminderFormViewModel(
    private val saveReminder: SaveReminderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderFormUiState())
    val uiState: StateFlow<ReminderFormUiState> = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title, error = null) }
    }

    fun onDateChange(date: String) {
        _uiState.update { it.copy(date = date, error = null) }
    }

    fun onTimeChange(time: String) {
        _uiState.update { it.copy(time = time, error = null) }
    }

    fun onPeriodicityChange(periodicity: String) {
        _uiState.update { it.copy(periodicity = periodicity, error = null) }
    }

    fun onSaveClick(onSaved: () -> Unit) {
        val state = _uiState.value
        val id = saveReminder(state.title, state.date, state.time, state.periodicity)
        if (id != null && id > 0) {
            onSaved()
        } else {
            _uiState.update { it.copy(error = "Заполните название, дату и время") }
        }
    }
}

class ReminderFormViewModelFactory(
    private val saveReminderUseCase: SaveReminderUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderFormViewModel::class.java)) {
            return ReminderFormViewModel(saveReminderUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
