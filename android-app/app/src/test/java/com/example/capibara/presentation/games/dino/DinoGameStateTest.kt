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

    /** Один шаг физики в наносекундах — 1/60 с. */
    private val stepNanos = 16_666_667L

    private fun newGame(): DinoGameState = DinoGameState().apply { configure(1080) }

    private fun DinoGameState.step(steps: Int = 1) {
        repeat(steps) { update(stepNanos) }
    }

    /**
     * Прогнать кактус до динозавра и прыгнуть в момент, когда его левый край
     * ближе [triggerX]. Возвращает false, если игра закончилась.
     */

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
        game.step()

        assertTrue("динозавр поднялся", game.dino.yPos < startY)
    }

    @Test
    fun `динозавр возвращается на землю`() {
        val game = newGame()

        game.jump()
        // Прыжок v=-52, g=1.2 длится около 87 шагов физики.
        game.step(120)

        assertEquals(DinoWorld.GROUND_Y, game.dino.yPos, 0.01f)
    }

    @Test
    fun `счёт растёт каждый кадр`() {
        val game = newGame()

        game.step(10)

        assertEquals(10, game.currentScore)
    }

    @Test
    fun `столкновение с кактусом завершает игру`() {
        val game = newGame()

        // Подтаскиваем кактус прямо под динозавра.
        game.cacti.first().xPos = DinoWorld.DINO_X.toInt()
        game.step()

        assertTrue("игра окончена", game.isGameOver)
    }

    @Test
    fun `после проигрыша счёт не растёт`() {
        val game = newGame()
        game.cacti.first().xPos = DinoWorld.DINO_X.toInt()
        game.step()
        val frozen = game.currentScore

        game.step(10)

        assertEquals(frozen, game.currentScore)
    }

    @Test
    fun `перезапуск обнуляет счёт и запоминает рекорд`() {
        val game = newGame()
        game.step(30)
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
        // шагов, и прыгаем в этот момент.
        cactus.xPos = DinoWorld.DINO_X.toInt() + 220
        game.jump()

        // Прокручиваем, пока кактус не пройдёт мимо динозавра.
        game.step(45)

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
        game.step()
        val yAfterDeath = game.dino.yPos

        game.jump()
        game.step()

        assertEquals(yAfterDeath, game.dino.yPos, 0.01f)
    }

    @Test
    fun `время кадра не влияет на скорость мира`() {
        val at60 = newGame()
        val at120 = newGame()
        // Одна и та же длительность — разные проценты кадров.
        val nanos = 1_000_000_000L
        at60.update(nanos)
        repeat(120) { at120.update(nanos / 120) }

        assertEquals(
            "за секунду на 60 и 120 Гц кактусы должны сдвинуться одинаково",
            at60.cacti.first().xPos,
            at120.cacti.first().xPos
        )
        assertEquals(at60.currentScore, at120.currentScore)
    }

    @Test
    fun `каждый кактус можно перепрыгнуть в своём окне`() {
        // Ставим кактус и прыгаем за 300 px до динозавра — окно, куда
        // попадает тайминг игрока. Раньше здесь стояло v=-40/g=3: кактус
        // высотой 132 px просто не проходил над динозавром ни при каком
        // моменте прыжка, и партия всегда обрывалась на третьем кактусе.
        listOf(0.85f, 1.0f, 1.2f).forEach { scale ->
            val game = newGame()
            val cactus = game.cacti.first()
            cactus.scale = scale
            cactus.xPos = DinoWorld.DINO_X.toInt() + 300
            game.jump()

            game.step(150)

            assertTrue(
                "кактус scale=$scale должен остаться позади, игра — продолжаться",
                !game.isGameOver && cactus.xPos < DinoWorld.DINO_X
            )
        }
    }

    @Test
    fun `кактусы не размножаются в одной точке`() {
        val game = newGame()
        val width = 1080

        repeat(600) { game.step() }

        val sorted = game.cacti.sortedBy { it.xPos }
        sorted.zipWithNext { left, right ->
            assertTrue(
                "между кактусами ${left.xPos} и ${right.xPos} должна быть дырка",
                right.xPos > left.xPos + DinoWorld.CACTUS_WIDTH * DinoWorld.CACTUS_MAX_SCALE
            )
        }
        // Новый кактус появляется за правым краем, а не посреди экрана.
        assertTrue("кактусы приходят справа", sorted.last().xPos > width)
    }

    @Test
    fun `динозавр остаётся жив при нескольких подряд прыжках`() {
        val game = newGame()
        // Каждый кактус перепрыгиваем в своём окне: как только он оказывается
        // в ~330 px перед динозавром, прыгаем. Так проверяется вся цепочка
        // спавна, а не один кактус.
        var frame = 0
        while (frame < 3000 && !game.isGameOver) {
            val next = game.cacti.minByOrNull { it.xPos }!!
            val triggerX = DinoWorld.DINO_X + DinoWorld.DINO_WIDTH + 150
            if (next.xPos in (triggerX - DinoWorld.EARTH_SPEED).toInt()..triggerX.toInt()) {
                game.jump()
            }
            game.step()
            frame++
        }

        assertFalse("игра должна продолжаться после нескольких кактусов", game.isGameOver)
    }

    @Test
    fun `конструктор не падает до получения ширины экрана`() {
        // Краш на устройстве: DinoGameState() создавался навигацией до того,
        // как Canvas вызывал configure(), и initClouds() бросал
        // IllegalArgumentException при deviceWidth = 0.
        val game = DinoGameState()

        assertEquals(DinoWorld.MAX_CLOUDS, game.clouds.size)
        game.step()
        assertEquals("без ширины мир не должен шагать", 0, game.currentScore)
    }

    @Test
    fun `restart без configure тоже безопасен`() {
        val game = DinoGameState()
        game.restart()

        assertEquals(DinoWorld.MAX_CLOUDS, game.clouds.size)
        assertEquals(1, game.generation)
    }
}
