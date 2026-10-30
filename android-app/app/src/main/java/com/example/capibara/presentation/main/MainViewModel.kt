package com.example.capibara.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capibara.domain.repository.InventoryRepository
import com.example.capibara.domain.repository.MoodRepository
import com.example.capibara.domain.repository.WalletRepository
import com.example.capibara.domain.usecase.GetNewsUseCase
import com.example.capibara.presentation.games.GameKind
import com.example.capibara.domain.usecase.GetPetStatsUseCase
import com.example.capibara.domain.usecase.GetRemindersUseCase
import com.example.capibara.domain.usecase.RefreshNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPetStats: GetPetStatsUseCase,
    private val getReminders: GetRemindersUseCase,
    private val getNews: GetNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase,
    private val walletRepository: WalletRepository,
    private val moodRepository: MoodRepository,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val today: String =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

    private val _uiState = MutableStateFlow(
        MainUiState(
            date = today,
            petStats = getPetStats(),
            reminders = getReminders(today),
            news = emptyList(),
            selectedTab = MainTab.SHOP
        )
    )

    init {
        viewModelScope.launch {
            walletRepository.observeCoins().collect { coins ->
                _uiState.update { it.copy(petStats = it.petStats?.copy(coins = coins)) }
            }
        }
        viewModelScope.launch {
            moodRepository.observeScore().collect { score ->
                _uiState.update { it.copy(petStats = it.petStats?.copy(moodScore = score)) }
            }
        }

        viewModelScope.launch {
            inventoryRepository.observeSelectedIds().collect { items ->
                _uiState.update { it.copy(selectedItemIds = items) }
            }
        }

        viewModelScope.launch {
            getNews().collect { articles ->
                _uiState.update { it.copy(news = articles) }
            }
        }

        loadNews(force = false)
    }

    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onTabSelected(index: MainTab) {
        _uiState.update {
            it.copy(
                selectedTab = index,
                isFormOpen = false,
                // Ушли с вкладки «Игры» — закрываем выбранную игру, чтобы
                // вернувшись увидеть список, а не открытую игру без меню.
                activeGame = if (index == MainTab.GAMES) it.activeGame else null
            )
        }
    }

    /** Открыть игру на весь экран. */
    fun onGameSelected(game: GameKind) {
        _uiState.update { it.copy(activeGame = game) }
    }

    /** Закрыть игру и вернуться к списку игр. */
    fun onExitGame() {
        _uiState.update { it.copy(activeGame = null) }
    }

    fun onOpenForm() {
        _uiState.update { it.copy(isFormOpen = true) }
    }

    fun onCloseForm() {
        _uiState.update { it.copy(isFormOpen = false) }
        refreshReminders(_uiState.value.date)
        refreshNews()
    }

    fun onDateSelected(date: String) {
        _uiState.update { it.copy(date = date, isToday = date == today) }
        refreshReminders(date)
    }

    fun onTodayClick() {
        _uiState.update { it.copy(date = today, isToday = true) }
        refreshReminders(today)
    }

    private fun refreshReminders(date: String) {
        _uiState.update { it.copy(reminders = getReminders(date)) }
    }

    fun refreshNews() {
        loadNews(force = true)
    }

    private fun loadNews(force: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, newsError = null) }
            refreshNewsUseCase(force)
                .onFailure { error ->
                    _uiState.update {
                        it.copy(newsError = error.message ?: "Не удалось загрузить новости")
                    }
                }
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
