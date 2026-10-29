package com.example.capibara.presentation.games

import org.junit.Assert.assertEquals
import org.junit.Test

class GameOverRewardTest {

    @Test
    fun `награда считается как очки умножить на монеты за очко`() {
        // Проверяем формулу награды: 7 очков => 7 * 5 = 35 монет.
        val score = 7
        val reward = score * SnakeViewModel.COINS_PER_POINT

        assertEquals(35, reward)
    }

    @Test
    fun `ноль очков даёт нулевую награду`() {
        val score = 0
        val reward = score * SnakeViewModel.COINS_PER_POINT

        assertEquals(0, reward)
    }

    @Test
    fun `кошелёк накапливает начисления`() {
        val wallet = FakeWalletRepository(startCoins = 500)
        kotlinx.coroutines.runBlocking {
            wallet.add(35)
        }
        assertEquals(listOf(35), wallet.added)
    }
}
