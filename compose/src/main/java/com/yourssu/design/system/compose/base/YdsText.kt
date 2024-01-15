package com.yourssu.design.system.compose.base

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextLayoutResult
import com.yourssu.design.system.compose.foundation.LocalContentColor
import com.yourssu.design.system.compose.foundation.YdsTextStyle

@Composable
fun YdsText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: YdsTextStyle = LocalTextStyle.current
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalYdsContentColor.current
        }
    }

    val mergedStyle = style.copy(color = textColor).toTextStyle()

    BasicText(
        text = text,
        modifier = modifier,
        style = mergedStyle,
        onTextLayout = onTextLayout,
    )
}

val LocalTextStyle = compositionLocalOf { YdsTextStyle.Default }

@Composable
fun ProvideTextStyle(value: YdsTextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}
