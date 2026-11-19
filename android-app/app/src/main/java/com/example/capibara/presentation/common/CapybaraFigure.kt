package com.example.capibara.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.graphics.drawscope.scale
import kotlin.math.round
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.capibara.domain.model.ClothingSprite
import com.example.capibara.domain.model.MoodLevel
import com.example.capibara.domain.model.PetSprite
import com.example.capibara.domain.model.SkinSprite

/**
 * Капибара с одеждой, нарисованная слоями в Compose.
 *
 * Композиция идёт на одном холсте 640×1000 в пикселях исходных спрайтов:
 * базовая капибара, затем одежда в порядке наложения из
 * [ClothingSprite.layersOf]. Все координаты и размеры заданы в этой системе,
 * экранный масштаб применяется один раз — слои не «разъезжаются».
 *
 * Вместо готовых склеек `pet_<одежда>_<эмоция>.png` здесь получается любая
 * комбинация из девяти предметов в четырёх эмоциях.
 */
@Composable
fun CapybaraFigure(
    mood: MoodLevel,
    itemIds: Set<Int>,
    modifier: Modifier = Modifier,
    skinId: Int? = null,
    contentDescription: String? = "Капибара"
) {
    val base = ImageBitmap.imageResource(PetSprite.base(mood))
    val skin = skinId?.let { SkinSprite.drawable(it) }
        ?.let { ImageBitmap.imageResource(it) }
    val layers = ClothingSprite.layersOf(itemIds).mapNotNull { key ->
        ClothingSprite.fitOnCanvas(key)?.let { fit ->
            key to (ImageBitmap.imageResource(ClothingSprite.drawable(key)) to fit)
        }
    }

    Canvas(
        modifier = modifier
            .aspectRatio(ClothingSprite.CANVAS_WIDTH / ClothingSprite.CANVAS_HEIGHT)
            .semantics {
                if (contentDescription != null) this.contentDescription = contentDescription
            }
    ) {
        val factor = size.width / ClothingSprite.CANVAS_WIDTH
        // Уменьшаем сглаживание: спрайты — пиксель-арт, «Low» даёт
        // ровно такой же результат, как Image(contentScale = Inside) раньше.
        scale(factor, pivot = Offset.Zero) {
            // Спрайты нужного размера: база — весь холст, слой — свой
            // прямоугольник из ClothingSprite.fitOnCanvas.
            if (skin != null) {
                // Скин — целая капибара: одежду на него не надевают.
                drawImage(skin, dstSize = canvasPixels())
                return@scale
            }
            drawImage(base, dstSize = canvasPixels())
            layers.forEach { (_, pair) ->
                val (image, fit) = pair
                translate(fit.x, fit.y) {
                    drawImage(
                        image = image,
                        dstSize = IntSize(
                            round(fit.width).toInt(),
                            round(fit.height).toInt()
                        )
                    )
                }
            }
        }
    }
}

/** Холст капибары в целых пикселях — формат, который ждёт drawImage. */
private fun canvasPixels(): IntSize = IntSize(
    round(ClothingSprite.CANVAS_WIDTH).toInt(),
    round(ClothingSprite.CANVAS_HEIGHT).toInt()
)
