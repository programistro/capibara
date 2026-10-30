package com.example.capibara.presentation.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capibara.domain.game.GameEngine
import com.example.capibara.domain.game.SnakeDirection
import com.example.capibara.domain.game.SnakeState
import com.example.capibara.domain.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnakeViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _gameOver = MutableStateFlow<GameOverResult?>(null)
    val gameOver: StateFlow<GameOverResult?> = _gameOver.asStateFlow()

    /** Монеты, заработанные в текущей партии. */
    private val _coinsEarned = MutableStateFlow(0)
    val coinsEarned: StateFlow<Int> = _coinsEarned.asStateFlow()

    private val engine = GameEngine(
        scope = viewModelScope,
        onGameOver = { score -> onGameOver(score) },
        onFoodEaten = { score -> awardForFood(score) }
    )

    val state: StateFlow<SnakeState> = engine.state

    // Игру НЕ запускаем в init: движок полз бы в фоне, пока пользователь
    // на других вкладках, и монеты капали бы незаметно. Старт — при открытии
    // вкладки «Игры» через [onScreenVisible].
    private var started = false

    /** Вызывается, когда вкладка игр показана пользователю. */
    fun onScreenVisible() {
        if (started) return
        started = true
        engine.start()
    }

    fun onDirectionChange(direction: SnakeDirection) {
        engine.changeDirection(direction)
    }

    fun onRestart() {
        _gameOver.value = null
        _coinsEarned.value = 0
        engine.restart()
        started = true
    }

    /** Начисление за съеденную еду — по монете за очко. */
    private fun awardForFood(score: Int) {
        // score уже включает текущую еду, поэтому начисляем ровно COINS_PER_POINT.
        viewModelScope.launch {
            walletRepository.add(COINS_PER_POINT)
            _coinsEarned.value += COINS_PER_POINT
        }
    }

    private fun onGameOver(score: Int) {
        // Награда уже начислена по ходу игры — здесь только итоги.
        _gameOver.value = GameOverResult(score = score, coins = _coinsEarned.value)
    }

    override fun onCleared() {
        super.onCleared()
        engine.reset()
    }

    companion object {
        /** Сколько монет даём за одну съеденную еду. */
        const val COINS_PER_POINT = 5
    }
}

data class GameOverResult(
    val score: Int,
    val coins: Int
)
