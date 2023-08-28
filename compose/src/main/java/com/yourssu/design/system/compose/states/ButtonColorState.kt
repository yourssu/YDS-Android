package com.yourssu.design.system.compose.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.yourssu.design.system.compose.foundation.pressedColorFor

@Immutable
class ButtonColorState(
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val bgColor: Color = Color.Transparent,
    val disabledBgColor: Color = Color.Transparent,
    pressed: Boolean = false,
) {
    var pressed by mutableStateOf(pressed)
        internal set

    @Composable
    fun contentColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> disabledContentColor
                pressed -> pressedColorFor(contentColor)
                else -> contentColor
            }
        )

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> disabledBgColor
                pressed -> pressedColorFor(bgColor)
                else -> bgColor
            }
        )
}