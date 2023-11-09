package com.yourssu.design.system.compose.rule

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val minBorderWidth = 0.34.dp
internal val normalBorderWidth = 1.dp
internal val thickBorderWidth = 8.dp

enum class YdsBorder(val dp: Dp) {
    Thin(minBorderWidth),
    Normal(normalBorderWidth),
    Thick(thickBorderWidth),
}
