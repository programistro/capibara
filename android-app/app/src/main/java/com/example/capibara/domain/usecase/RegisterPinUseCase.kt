package com.example.capibara.domain.usecase

import com.example.capibara.domain.repository.AuthRepository
import javax.inject.Inject

sealed interface RegisterResult {
    data object Success : RegisterResult
    data object TooShort : RegisterResult
    data object Mismatch : RegisterResult
}

class RegisterPinUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(pin: String, repeatPin: String): RegisterResult {
        if (pin.length < LoginWithPinUseCase.PIN_LENGTH) return RegisterResult.TooShort
        if (pin != repeatPin) return RegisterResult.Mismatch
        repository.savePin(pin)
        return RegisterResult.Success
    }
}
