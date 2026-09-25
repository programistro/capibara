package com.example.capibara.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capibara.presentation.auth.AuthScreen
import com.example.capibara.presentation.auth.AuthViewModel
import com.example.capibara.presentation.main.MainScreen
import com.example.capibara.presentation.main.MainTab
import com.example.capibara.presentation.main.MainViewModel
import com.example.capibara.presentation.main.ReminderFormViewModel
import com.example.capibara.presentation.registration.RegistrationScreen
import com.example.capibara.presentation.registration.RegistrationViewModel
import com.example.capibara.presentation.shop.ShopViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppNavGraph(startDestination: String) {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.Auth.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            AuthScreen(
                state = state,
                onPinChange = viewModel::onPinChange,
                onLoginClick = {
                    viewModel.onLoginClick {
                        navController.navigate(Route.Main.route) {
                            popUpTo(Route.Auth.route) { inclusive = true }
                        }
                    }
                },
                onRegisterClick = { navController.navigate(Route.Registration.route) }
            )
        }
        composable(Route.Registration.route) {
            val viewModel: RegistrationViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            RegistrationScreen(
                state = state,
                onPinChange = viewModel::onPinChange,
                onRepeatPinChange = viewModel::onRepeatPinChange,
                onRegisterClick = {
                    viewModel.onRegisterClick {
                        navController.navigate(Route.Main.route) {
                            popUpTo(Route.Auth.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Route.Main.route) {
            val mainViewModel: MainViewModel = hiltViewModel()
            val shopViewModel: ShopViewModel = hiltViewModel()
            val formViewModel: ReminderFormViewModel = hiltViewModel()
            val state by mainViewModel.uiState.collectAsStateWithLifecycle()
            val shopState by shopViewModel.uiState.collectAsStateWithLifecycle()
            val formState by formViewModel.uiState.collectAsStateWithLifecycle()
            MainScreen(
                state = state,
                shopState = shopState,
                formState = formState,
                onTabSelected = mainViewModel::onTabSelected,
                onAddClick = mainViewModel::onOpenForm,
                onTodayClick = mainViewModel::onTodayClick,
                onDateSelected = mainViewModel::onDateSelected,
                onBuyClick = shopViewModel::onBuyClick,
                onClearError = shopViewModel::onClearError,
                onShopBackClick = { mainViewModel.onTabSelected(MainTab.HOME) },
                onFormTitleChange = formViewModel::onTitleChange,
                onFormDateChange = formViewModel::onDateChange,
                onFormTimeChange = formViewModel::onTimeChange,
                onFormPeriodicityChange = formViewModel::onPeriodicityChange,
                onFormNotifyEnabledChange = formViewModel::onNotifyEnabledChange,
                onFormNotifyMinutesChange = formViewModel::onNotifyMinutesChange,
                onFormSaveClick = {
                    formViewModel.onSaveClick { triggerAt ->
                        val text = triggerAt?.let {
                            SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
                                .format(Date(it))
                        }
                        Toast.makeText(
                            context,
                            if (text != null) {
                                "Напоминание придёт: $text"
                            } else {
                                "Напоминание сохранено без уведомления"
                            },
                            Toast.LENGTH_LONG
                        ).show()
                        mainViewModel.onCloseForm()
                    }
                },
                onFormClose = mainViewModel::onCloseForm
            )
        }
    }
}
