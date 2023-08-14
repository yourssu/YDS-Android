package com.yourssu.design.system.compose.rule

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val minBorderWidth = 0.34.dp
internal val normalBorderWidth = 1.dp
internal val thickBorderWidth = 8.dp

data class Border(
    val thin: Dp = minBorderWidth,
    val normal: Dp = normalBorderWidth,
    val thick: Dp = thickBorderWidth
)

internal val YdsBorder = Border()

internal val LocalYdsBorder = staticCompositionLocalOf { YdsBorder }