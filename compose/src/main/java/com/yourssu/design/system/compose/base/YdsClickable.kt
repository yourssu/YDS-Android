package com.yourssu.design.system.compose.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * (전) noRippleClickable
 *
 * ripple 효과가 없는 clickable입니다.
 */
fun Modifier.ydsClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    this.ydsClickable(
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled,
        onClick = onClick,
    )
}

fun Modifier.ydsClickable(
    interactionSource: MutableInteractionSource,
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = this.clickable(
    interactionSource = interactionSource,
    indication = null,
    enabled = enabled,
) {
    onClick()
}
