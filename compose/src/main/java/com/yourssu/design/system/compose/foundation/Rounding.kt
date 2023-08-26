package com.yourssu.design.system.compose.foundation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

internal val smallRounding = RoundedCornerShape(2.dp)
internal val normalRounding = RoundedCornerShape(4.dp)
internal val largeRounding = RoundedCornerShape(8.dp)

internal val LocalYdsRounding = compositionLocalOf { normalRounding }
