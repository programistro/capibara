package com.example.capibara.domain.game

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Проверяет, что монеты начисляются за каждую съеденную еду, а не только
 * в момент проигрыша. Раньше награда висела на onGameOver, который срабатывает
 * лишь при столкновении с собственным телом — до этого очки никуда не шли.
 */
class FoodRewardTest {

    @Test
    fun `съеденная еда вызывает начисление`() = runTest {
        val eaten = mutableListOf<Int>()
        val engine = GameEngine(
            scope = backgroundScope,   // цикл движка бесконечный — гасим вместе с тестом
            stepDelayMillis = 10,
            onFoodEaten = { score -> eaten.add(score) }
        )

        // Еда лежит на (5, 5), змейка стартует на (16, 16).
        // Едем вверх до y=5, затем влево до x=5 — за 10 шагов вверх и 11 влево.
        engine.start()
        testScheduler.runCurrent()          // даём корутине сделать первый delay
        engine.changeDirection(SnakeDirection.UP)
        testScheduler.advanceTimeBy(110)    // ровно 11 шагов вверх: y 16 -> 5
        testScheduler.runCurrent()
        engine.changeDirection(SnakeDirection.LEFT)
        testScheduler.advanceTimeBy(190)    // 11 шагов + запас: доходим до x = 5
        testScheduler.runCurrent()

        assertEquals("еда съедена ровно один раз", 1, eaten.size)
        assertEquals("очко засчитано", 1, eaten.first())
        assertEquals(1, engine.state.value.score)
    }

    @Test
    fun `без еды начисления нет`() = runTest {
        val eaten = mutableListOf<Int>()
        val engine = GameEngine(
            scope = backgroundScope,
            stepDelayMillis = 10,
            onFoodEaten = { score -> eaten.add(score) }
        )

        engine.start()
        testScheduler.runCurrent()
        testScheduler.advanceTimeBy(50) // 5 шагов вправо от (16,16) — еда далеко
        testScheduler.runCurrent()

        assertEquals(0, eaten.size)
        assertEquals(0, engine.state.value.score)
    }

    @Test
    fun `начисление идёт за каждую еду`() = runTest {
        val eaten = mutableListOf<Int>()
        val engine = GameEngine(
            scope = backgroundScope,
            stepDelayMillis = 10,
            onFoodEaten = { score -> eaten.add(score) }
        )

        engine.start()
        testScheduler.runCurrent()          // даём корутине сделать первый delay
        engine.changeDirection(SnakeDirection.UP)
        testScheduler.advanceTimeBy(110)    // ровно 11 шагов вверх: y 16 -> 5
        testScheduler.runCurrent()
        engine.changeDirection(SnakeDirection.LEFT)
        testScheduler.advanceTimeBy(190)    // 11 шагов + запас: доходим до x = 5
        testScheduler.runCurrent()

        // После первой еды змейка на (5,5) и растёт; проверяем, что счётчик
        // накоплений растёт вместе со счётом.
        val firstScore = engine.state.value.score
        assertEquals(1, firstScore)
        assertEquals(listOf(1), eaten)
    }
}
