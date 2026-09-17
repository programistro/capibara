package com.example.capibara.presentation.main

import androidx.lifecycle.ViewModel
import com.example.capibara.domain.model.DEFAULT_NOTIFY_BEFORE_MINUTES
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

    fun onNotifyEnabledChange(enabled: Boolean) {
        _uiState.update { it.copy(notifyEnabled = enabled, error = null) }
    }

    fun onNotifyMinutesChange(minutes: String) {
        _uiState.update {
            it.copy(
                notifyMinutes = minutes.filter(Char::isDigit).take(4),
                error = null
            )
        }
    }

    fun onSaveClick(onSaved: (triggerAtMillis: Long?) -> Unit) {
        val state = _uiState.value
        val minutes = state.notifyMinutes.toIntOrNull() ?: DEFAULT_NOTIFY_BEFORE_MINUTES
        val result = saveReminder(
            state.title,
            state.date,
            state.time,
            state.periodicity,
            minutes,
            state.notifyEnabled
        )
        if (result != null && result.id > 0) {
            onSaved(result.triggerAtMillis)
        } else {
            _uiState.update { it.copy(error = "Заполните название, дату и время") }
        }
    }
}
