package com.example.capibara.domain.usecase

import com.example.capibara.domain.repository.AuthRepository
import javax.inject.Inject

class HasPinUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.hasPin()
}
