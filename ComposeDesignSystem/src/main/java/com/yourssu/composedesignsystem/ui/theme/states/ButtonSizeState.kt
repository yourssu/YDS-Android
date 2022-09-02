package com.yourssu.composedesignsystem.ui.theme.states

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize

data class ButtonSizeState(
    val typo: TextStyle,
    val iconSize: IconSize,
    val height: Dp,
    val horizontalPadding: Dp
)
