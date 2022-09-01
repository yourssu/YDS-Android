package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize

enum class BoxButtonType {
    Filled,
    Tinted,
    Line
}

sealed class BoxButtonSize(val height: Dp, val iconSize: IconSize) {
    object ExtraLarge : BoxButtonSize(56.dp, IconSize.Medium) {
        val typo: TextStyle
            @Composable get() = YdsTheme.typography.button1
    }
}