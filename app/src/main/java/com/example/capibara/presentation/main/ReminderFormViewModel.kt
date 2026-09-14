package com.example.capibara.presentation.main

import androidx.lifecycle.ViewModel
import com.example.capibara.domain.usecase.SaveReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReminderFormViewModel @Inject constructor(
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
