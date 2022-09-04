package com.yourssu.composedesignsystem.ui.theme.rule

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val minWidth = 0.34.dp

data class Border(
    val thin: Dp = minWidth,
    val normal: Dp = 1.dp,
    val thick: Dp = 8.dp
)

internal val YdsBorder = Border()

internal val LocalYdsBorder = staticCompositionLocalOf { YdsBorder }