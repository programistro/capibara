package com.example.capibara.presentation.registration

data class RegistrationUiState(
    val pin: String = "",
    val repeatPin: String = "",
    val error: String? = null
)
