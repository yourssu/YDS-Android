package com.yourssu.design.system.compose.foundation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

internal val smallRounding = 2.dp
internal val normalRounding = 4.dp
internal val largeRounding = 8.dp

data class YdsRounding(
    val small: RoundedCornerShape = RoundedCornerShape(smallRounding),
    val medium: RoundedCornerShape = RoundedCornerShape(normalRounding),
    val large: RoundedCornerShape = RoundedCornerShape(largeRounding),
)

internal val LocalYdsRounding = staticCompositionLocalOf { YdsRounding() }