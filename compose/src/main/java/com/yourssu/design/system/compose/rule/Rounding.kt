package com.yourssu.design.system.compose.rule

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

internal val smallRounding = 2.dp
internal val normalRounding = 4.dp
internal val largeRounding = 8.dp

internal val YdsRounding = Shapes(
    small = RoundedCornerShape(smallRounding),
    medium = RoundedCornerShape(normalRounding),
    large = RoundedCornerShape(largeRounding)
)

internal val LocalYdsRounding = staticCompositionLocalOf { YdsRounding }