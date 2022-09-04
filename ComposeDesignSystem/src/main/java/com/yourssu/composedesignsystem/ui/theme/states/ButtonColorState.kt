package com.yourssu.composedesignsystem.ui.theme.states

import androidx.compose.ui.graphics.Color

data class ButtonColorState(
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val warnedContentColor: Color = Color.Unspecified,
    val bgColor: Color = Color.Transparent,
    val disabledBgColor: Color = Color.Transparent,
    val warnedBgColor: Color = Color.Transparent
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
