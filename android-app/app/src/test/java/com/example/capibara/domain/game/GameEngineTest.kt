package com.example.capibara.domain.game

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameEngineTest {

    @Test
    fun `начальное состояние корректно`() = runTest {
        val engine = GameEngine(scope = this, stepDelayMillis = 10_000)

        val state = engine.state.value
        assertEquals(0, state.score)
        assertEquals(1, state.snake.size)
        assertEquals(16 to 16, state.snake.first())
        assertEquals(SnakeDirection.RIGHT, state.direction)
        assertFalse(state.isGameOver)
    }

    @Test
    fun `разворот на 180 градусов игнорируется`() = runTest {
        val engine = GameEngine(scope = this, stepDelayMillis = 10_000)

        engine.changeDirection(SnakeDirection.LEFT)

        // Направление в состоянии не изменилось.
        assertEquals(SnakeDirection.RIGHT, engine.state.value.direction)
    }

    @Test
    fun `два быстрых поворота не дают разворота в себя`() = runTest {
        val engine = GameEngine(scope = this, stepDelayMillis = 10)
        engine.start()

        // Едем вправо. Нажимаем вверх, затем сразу вниз — это разворот
        // относительно намерения «вверх», значит второе нажатие игнорируется.
        engine.changeDirection(SnakeDirection.UP)
        engine.changeDirection(SnakeDirection.DOWN)

        kotlinx.coroutines.delay(40)

        // Через несколько шагов змейка должна идти вверх (y уменьшается),
        // а не вниз — иначе она бы развернулась сама в себя.
        val head = engine.state.value.snake.first()
        engine.reset()
        assertTrue("голова должна уйти выше стартовой строки, получено $head", head.second < 16)
    }

    @Test
    fun `сброс возвращает к началу`() = runTest {
        val engine = GameEngine(scope = this, stepDelayMillis = 10_000)
        engine.changeDirection(SnakeDirection.UP)
        engine.reset()

        assertEquals(SnakeDirection.RIGHT, engine.state.value.direction)
        assertEquals(16 to 16, engine.state.value.snake.first())
        assertEquals(0, engine.state.value.score)
        assertFalse(engine.state.value.isGameOver)
    }

    @Test
    fun `змейка двигается пока идёт игра`() = runTest {
        val engine = GameEngine(scope = this, stepDelayMillis = 10)
        engine.start()

        kotlinx.coroutines.delay(100)
        val after = engine.state.value
        engine.reset()

        // За 100 мс при шаге 10 мс змейка должна была сдвинуться (или телепортироваться).
        assertNotEquals(16 to 16, after.snake.first())
    }

    @Test
    fun `константы движка`() {
        assertEquals(32, GameEngine.BOARD_SIZE)
        assertTrue(GameEngine.DEFAULT_STEP_DELAY > 0)
    }
}
