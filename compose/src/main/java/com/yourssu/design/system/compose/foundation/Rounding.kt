package com.yourssu.design.system.compose.foundation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

private val smallRounding = RoundedCornerShape(2.dp)
private val normalRounding = RoundedCornerShape(4.dp)
private val largeRounding = RoundedCornerShape(8.dp)

data class YdsRounding(
    val small: RoundedCornerShape = smallRounding,
    val medium: RoundedCornerShape = normalRounding,
    val large: RoundedCornerShape = largeRounding
)

internal val LocalYdsRounding = compositionLocalOf { YdsRounding() }