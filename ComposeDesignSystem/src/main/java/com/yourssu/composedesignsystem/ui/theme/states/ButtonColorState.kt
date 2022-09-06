package com.yourssu.composedesignsystem.ui.theme.states

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.yourssu.composedesignsystem.ui.theme.util.alterColorIfPressed

data class ButtonColorState(
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val warnedContentColor: Color = Color.Unspecified,
    val pointedContentColor: Color = Color.Unspecified,
    val bgColor: Color = Color.Transparent,
    val disabledBgColor: Color = Color.Transparent,
    val warnedBgColor: Color = Color.Transparent,
    val pointedBgColor: Color = Color.Transparent
) {
    @Composable
    fun contentColor(
        enabled: Boolean,
        interactionSource: MutableInteractionSource? = null
    ): State<Color> {
        val pressed = interactionSource?.collectIsPressedAsState()?.value

        return rememberUpdatedState(if (enabled) {
            contentColor.alterColorIfPressed(isPressed = pressed ?: false)
        } else {
            disabledContentColor
        })
    }

    @Composable
    fun backgroundColor(
        enabled: Boolean,
        interactionSource: MutableInteractionSource
    ): State<Color> {
        val pressed by interactionSource.collectIsPressedAsState()

        return rememberUpdatedState(if (enabled) {
            bgColor.alterColorIfPressed(isPressed = pressed)
        } else {
            disabledBgColor
        })
    }
}