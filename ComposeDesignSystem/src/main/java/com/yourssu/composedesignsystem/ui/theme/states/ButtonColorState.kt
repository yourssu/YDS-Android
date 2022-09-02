package com.yourssu.composedesignsystem.ui.theme.states

import androidx.compose.ui.graphics.Color

data class ButtonColorState(
    val contentColor: Color,
    val disabledContentColor: Color,
    val warnedContentColor: Color,
    val bgColor: Color,
    val disabledBgColor: Color,
    val warnedBgColor: Color
) {
    constructor(
        contentColor: Color,
        disabledContentColor: Color,
        warnedContentColor: Color,
        bgColor: Color
    ) : this(
        contentColor,
        disabledContentColor,
        warnedContentColor,
        bgColor, bgColor, bgColor
    )
}
