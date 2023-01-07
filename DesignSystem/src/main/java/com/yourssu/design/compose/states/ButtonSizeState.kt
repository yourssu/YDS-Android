package com.yourssu.design.compose.states

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.compose.foundation.IconSize

@Immutable
data class ButtonSizeState(
    val typo: TextStyle = TextStyle.Default,
    val iconSize: IconSize = IconSize.Medium,
    val height: Dp = 0.dp,
    val horizontalPadding: Dp = 0.dp,
    val betweenSpace: Dp = 0.dp
)
