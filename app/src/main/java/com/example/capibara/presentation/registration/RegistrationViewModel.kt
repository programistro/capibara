package com.example.capibara.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capibara.domain.usecase.LoginWithPinUseCase
import com.example.capibara.domain.usecase.RegisterPinUseCase
import com.example.capibara.domain.usecase.RegisterResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel(
    private val registerPin: RegisterPinUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onPinChange(pin: String) {
        _uiState.update {
            it.copy(
                pin = pin.filter(Char::isDigit).take(LoginWithPinUseCase.PIN_LENGTH),
                error = null
            )
        }
    }

    fun onRepeatPinChange(pin: String) {
        _uiState.update {
            it.copy(
                repeatPin = pin.filter(Char::isDigit).take(LoginWithPinUseCase.PIN_LENGTH),
                error = null
            )
        }
    }

    fun onRegisterClick(onSuccess: () -> Unit) {
        val state = _uiState.value
        when (registerPin(state.pin, state.repeatPin)) {
            RegisterResult.Success -> onSuccess()
            RegisterResult.TooShort -> _uiState.update {
                it.copy(error = "Пин-код должен содержать 4 цифры")
            }
            RegisterResult.Mismatch -> _uiState.update {
                it.copy(error = "Пин-коды не совпадают")
            }
        }
    }
}

class RegistrationViewModelFactory(
    private val registerPinUseCase: RegisterPinUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(registerPinUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
