package com.example.capibara.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capibara.di.AppComponent
import com.example.capibara.presentation.auth.AuthScreen
import com.example.capibara.presentation.auth.AuthViewModel
import com.example.capibara.presentation.auth.AuthViewModelFactory
import com.example.capibara.presentation.main.MainScreen
import com.example.capibara.presentation.main.MainTab
import com.example.capibara.presentation.main.MainViewModel
import com.example.capibara.presentation.main.MainViewModelFactory
import com.example.capibara.presentation.main.ReminderFormViewModel
import com.example.capibara.presentation.main.ReminderFormViewModelFactory
import com.example.capibara.presentation.registration.RegistrationScreen
import com.example.capibara.presentation.registration.RegistrationViewModel
import com.example.capibara.presentation.registration.RegistrationViewModelFactory
import com.example.capibara.presentation.shop.ShopViewModel
import com.example.capibara.presentation.shop.ShopViewModelFactory

@Composable
fun AppNavGraph(
    appComponent: AppComponent,
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.Auth.route) {
            val viewModel: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(appComponent.loginWithPinUseCase())
            )
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
            val viewModel: RegistrationViewModel = viewModel(
                factory = RegistrationViewModelFactory(appComponent.registerPinUseCase())
            )
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
            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(
                    appComponent.getPetStatsUseCase(),
                    appComponent.getRemindersUseCase()
                )
            )
            val shopViewModel: ShopViewModel = viewModel(
                factory = ShopViewModelFactory(appComponent.getShopItemsUseCase())
            )
            val formViewModel: ReminderFormViewModel = viewModel(
                factory = ReminderFormViewModelFactory(appComponent.saveReminderUseCase())
            )
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
                onBuyClick = shopViewModel::onBuyClick,
                onShopBackClick = { mainViewModel.onTabSelected(MainTab.HOME) },
                onFormTitleChange = formViewModel::onTitleChange,
                onFormDateChange = formViewModel::onDateChange,
                onFormTimeChange = formViewModel::onTimeChange,
                onFormPeriodicityChange = formViewModel::onPeriodicityChange,
                onFormSaveClick = {
                    formViewModel.onSaveClick { mainViewModel.onCloseForm() }
                },
                onFormClose = mainViewModel::onCloseForm
            )
        }
    }
}
