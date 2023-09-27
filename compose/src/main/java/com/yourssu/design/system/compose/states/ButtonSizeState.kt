package com.yourssu.design.system.compose.states

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.foundation.YdsTextStyle

@Immutable
data class ButtonSizeState(
    val typo: YdsTextStyle = YdsTextStyle.Default,
    val iconSize: IconSize = IconSize.Medium,
    val height: Dp = 0.dp,
    val horizontalPadding: Dp = 0.dp,
    val betweenSpace: Dp = 0.dp
)
