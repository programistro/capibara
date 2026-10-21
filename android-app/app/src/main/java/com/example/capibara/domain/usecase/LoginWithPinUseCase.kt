package com.example.capibara.domain.usecase

import com.example.capibara.domain.repository.AuthRepository
import javax.inject.Inject

class LoginWithPinUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(pin: String): Boolean {
        if (pin.length < PIN_LENGTH) return false
        return repository.checkPin(pin)
    }

    companion object {
        const val PIN_LENGTH = 4
    }
}
