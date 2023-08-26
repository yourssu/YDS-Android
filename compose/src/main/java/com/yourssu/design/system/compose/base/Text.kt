package com.yourssu.design.system.compose.base

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import com.yourssu.design.system.compose.foundation.LocalContentColor
import com.yourssu.design.system.compose.foundation.YdsTextStyle

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: YdsTextStyle = LocalTextStyle.current
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current
        }
    }

    val mergedStyle = style.copy(color = textColor).toTextStyle()

    BasicText(
        text = text,
        modifier = modifier,
        style = mergedStyle,
    )
}

val LocalTextStyle = compositionLocalOf { YdsTextStyle.Default }
