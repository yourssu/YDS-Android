package com.yourssu.design.system.compose.base

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import com.yourssu.design.system.compose.foundation.LocalYdsContentAlpha
import com.yourssu.design.system.compose.foundation.LocalYdsContentColor
import com.yourssu.design.system.compose.foundation.YdsTextStyle

@Composable
fun YdsText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: YdsTextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalYdsContentColor.current.copy(alpha = LocalYdsContentAlpha.current)
        }
    }

    val mergedStyle = style.copy(color = textColor).toTextStyle()

    BasicText(
        text = text,
        modifier = modifier,
        style = mergedStyle,
        onTextLayout = onTextLayout,
        overflow = overflow,
        maxLines = maxLines,
        minLines = minLines,
    )
}

val LocalTextStyle = compositionLocalOf { YdsTextStyle.Default }

@Composable
fun ProvideTextStyle(value: YdsTextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}
