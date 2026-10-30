package com.example.capibara.presentation.games.dino

import com.example.capibara.presentation.games.dino.model.DinoWorld
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Проверяем, что игра «Динозавр» действительно заканчивается при столкновении.
 * В исходной игре на GitHub этого нет: объекты жили в глобальных `var`,
 * и протестировать их без Android нельзя.
 */
class DinoGameStateTest {

    private fun newGame(): DinoGameState = DinoGameState().apply { configure(1080) }

    @Test
    fun `стартовое состояние корректно`() {
        val game = newGame()

        assertEquals(0, game.currentScore)
        assertFalse(game.isGameOver)
        assertEquals(DinoWorld.DINO_X, game.dino.xPos)
        assertTrue("кактусы созданы", game.cacti.isNotEmpty())
    }

    @Test
    fun `прыжок поднимает динозавра над землёй`() {
        val game = newGame()
        val startY = game.dino.yPos

        game.jump()
        game.update()

        assertTrue("динозавр поднялся", game.dino.yPos < startY)
    }

    @Test
    fun `динозавр возвращается на землю`() {
        val game = newGame()

        game.jump()
        // Прыжок длится примерно 27 кадров при velocity -40 и gravity 3.
        repeat(40) { game.update() }

        assertEquals(DinoWorld.GROUND_Y, game.dino.yPos, 0.01f)
    }

    @Test
    fun `счёт растёт каждый кадр`() {
        val game = newGame()

        repeat(10) { game.update() }

        assertEquals(10, game.currentScore)
    }

    @Test
    fun `столкновение с кактусом завершает игру`() {
        val game = newGame()

        // Подтаскиваем кактус прямо под динозавра.
        game.cacti.first().xPos = DinoWorld.DINO_X.toInt()
        game.update()

        assertTrue("игра окончена", game.isGameOver)
    }

    @Test
    fun `после проигрыша счёт не растёт`() {
        val game = newGame()
        game.cacti.first().xPos = DinoWorld.DINO_X.toInt()
        game.update()
        val frozen = game.currentScore

        repeat(10) { game.update() }

        assertEquals(frozen, game.currentScore)
    }

    @Test
    fun `перезапуск обнуляет счёт и запоминает рекорд`() {
        val game = newGame()
        repeat(30) { game.update() }
        val scored = game.currentScore

        game.restart()

        assertEquals(0, game.currentScore)
        assertEquals(scored, game.highScore)
        assertFalse(game.isGameOver)
    }

    @Test
    fun `кактус стоит на земле, а не висит над ней`() {
        val game = newGame()

        game.cacti.forEach { cactus ->
            // Низ кактуса должен быть на линии земли или чуть ниже,
            // а не висеть высоко над ней.
            assertTrue(
                "низ кактуса ${cactus.yPos} должен быть у земли ${DinoWorld.GROUND_Y}",
                cactus.yPos >= DinoWorld.GROUND_Y
            )
            assertTrue(
                "низ кактуса не должен проваливаться слишком глубоко",
                cactus.yPos <= DinoWorld.GROUND_Y + 50
            )
        }
    }

    @Test
    fun `кактус можно перепрыгнуть`() {
        val game = newGame()
        val cactus = game.cacti.first()

        // Ставим кактус так, чтобы он доехал до динозавра через несколько
        // кадров, и прыгаем в этот момент.
        cactus.xPos = DinoWorld.DINO_X.toInt() + 140
        game.jump()

        // Прокручиваем, пока кактус не пройдёт мимо динозавра.
        repeat(45) { game.update() }

        assertTrue(
            "кактус проехал мимо, а игра продолжается",
            !game.isGameOver && cactus.xPos < DinoWorld.DINO_X
        )
    }

    @Test
    fun `рестарт увеличивает номер партии`() {
        val game = newGame()
        val before = game.generation

        game.restart()

        assertEquals("номер партии растёт — UI по нему перезапускает цикл",
            before + 1, game.generation)
    }

    @Test
    fun `прыжок недоступен после проигрыша`() {
        val game = newGame()
        game.cacti.first().xPos = DinoWorld.DINO_X.toInt()
        game.update()
        val yAfterDeath = game.dino.yPos

        game.jump()
        game.update()

        assertEquals(yAfterDeath, game.dino.yPos, 0.01f)
    }
}
