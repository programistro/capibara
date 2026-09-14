package com.example.capibara.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capibara.domain.usecase.LoginWithPinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(
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

class AuthViewModelFactory(
    private val loginWithPinUseCase: LoginWithPinUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(loginWithPinUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
