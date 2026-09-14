package com.example.capibara.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.capibara.domain.usecase.LoginWithPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginWithPin: LoginWithPinUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onPinChange(pin: String) {
        _uiState.update {
            it.copy(
                pin = pin.filter(Char::isDigit).take(LoginWithPinUseCase.PIN_LENGTH),
                error = null
            )
        }
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        if (loginWithPin(_uiState.value.pin)) {
            onSuccess()
        } else {
            _uiState.update { it.copy(error = "Неверный пин-код") }
        }
    }
}
