package com.yourssu.composedesignsystem.ui.theme.states

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize

data class ButtonSizeState(
    val typo: TextStyle = TextStyle.Default,
    val iconSize: IconSize = IconSize.Medium,
    val height: Dp = 0.dp,
    val horizontalPadding: Dp = 0.dp
)
