package com.yourssu.design.compose.states

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.yourssu.design.compose.util.alterColorIfPressed

@Immutable
data class ButtonColorState(
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val bgColor: Color = Color.Transparent,
    val disabledBgColor: Color = Color.Transparent
) {
    @Composable
    fun contentColor(
        enabled: Boolean,
        interactionSource: MutableInteractionSource
    ): State<Color> {
        val pressed = interactionSource.collectIsPressedAsState().value

        return rememberUpdatedState(if (enabled) {
            contentColor.alterColorIfPressed(isPressed = pressed)
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