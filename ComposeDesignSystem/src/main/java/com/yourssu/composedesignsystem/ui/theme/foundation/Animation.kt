package com.yourssu.composedesignsystem.ui.theme.foundation

import androidx.compose.animation.core.CubicBezierEasing

internal val YdsInAndOutEasing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)

enum class Duration(val millis: Int) {
    Short(100),
    Long(250)
}