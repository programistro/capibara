package com.example.capibara.presentation.common

import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.linkify.LinkifyPlugin

/**
 * Markdown-рендер для тела статьи.
 *
 * API отдаёт `content` в Markdown (заголовки, **жирный**, таблицы, списки),
 * а Compose `Text` показывает такую разметку как обычный текст — с сырыми
 * `#`, `**` и `|`. Рендерим через `Markwon` в `TextView`.
 *
 * [textColor] — цвет текста из темы; в тёмной теме по умолчанию был бы чёрный.
 */
@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified,
) {
    val context = LocalContext.current

    // Markwon дорогой в создании (парсер + плагины), держим один на время жизни.
    val markwon = remember {
        Markwon.builder(context)
            .usePlugin(TablePlugin.create(context))
            .usePlugin(LinkifyPlugin.create())
            .build()
    }

    val rendered = remember(markdown, textColor) {
        val spanned = markwon.toMarkdown(markdown)
        if (textColor == Color.Unspecified) {
            SpannedString(spanned)
        } else {
            // Цвет накладываем span'ом: Markwon задаёт свои стили для заголовков
            // и таблиц, и просто setTextColor на TextView их не перекрасит.
            SpannedString(
                SpannableString(spanned).apply {
                    setSpan(
                        ForegroundColorSpan(textColor.toArgb()),
                        0,
                        length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            )
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            TextView(ctx).apply {
                setTextIsSelectable(true)
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = { view ->
            view.setTextColor(
                if (textColor == Color.Unspecified) view.currentTextColor
                else textColor.toArgb()
            )
            view.text = rendered
        }
    )
}
