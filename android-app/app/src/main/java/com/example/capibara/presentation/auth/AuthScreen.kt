package com.example.capibara.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.presentation.common.AuthCard
import com.example.capibara.presentation.common.GreenButton
import com.example.capibara.presentation.common.PinTextField
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.ScreenBackground

@Composable
fun AuthScreen(
    state: AuthUiState,
    onPinChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .systemBarsPadding()
            .imePadding()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        AuthCard(title = "Авторизация") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Пин-код",
                    fontSize = 24.sp,
                    color = Color.Black
                )
                PinTextField(
                    value = state.pin,
                    onValueChange = onPinChange,
                    placeholder = "Введите пин-код"
                )
                state.error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                GreenButton(
                    text = "Войти",
                    onClick = onLoginClick
                )
                Text(
                    text = "Нет аккаунта? Зарегистрироваться",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onRegisterClick)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    CapibaraTheme {
        AuthScreen(
            state = AuthUiState(),
            onPinChange = {},
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenErrorPreview() {
    CapibaraTheme {
        AuthScreen(
            state = AuthUiState(pin = "1234", error = "Неверный пин-код"),
            onPinChange = {},
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}
