package com.yourssu.composedesignsystem.ui.theme.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

inline fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource?,
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}