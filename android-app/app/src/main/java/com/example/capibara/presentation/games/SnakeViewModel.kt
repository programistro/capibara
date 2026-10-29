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

    private val _coinsEarned = MutableStateFlow(0)
    val coinsEarned: StateFlow<Int> = _coinsEarned.asStateFlow()

    private val engine = GameEngine(
        scope = viewModelScope,
        onGameOver = { score -> onGameOver(score) }
    )

    val state: StateFlow<SnakeState> = engine.state

    init {
        engine.start()
    }

    fun onDirectionChange(direction: SnakeDirection) {
        engine.changeDirection(direction)
    }

    fun onRestart() {
        _gameOver.value = null
        _coinsEarned.value = 0
        engine.restart()
    }

    private fun onGameOver(score: Int) {
        val reward = score * COINS_PER_POINT
        _gameOver.value = GameOverResult(score = score, coins = reward)
        if (reward > 0) {
            viewModelScope.launch {
                walletRepository.add(reward)
                _coinsEarned.value = reward
            }
        }
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
