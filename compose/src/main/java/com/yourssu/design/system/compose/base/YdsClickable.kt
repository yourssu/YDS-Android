package com.yourssu.design.system.compose.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

/**
 * (전) noRippleClickable
 *
 * ripple 효과가 없는 clickable입니다.
 */
inline fun Modifier.ydsClickable(
    interactionSource: MutableInteractionSource,
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = this.clickable(
    interactionSource = interactionSource,
    indication = null,
    enabled = enabled,
) {
    onClick()
}