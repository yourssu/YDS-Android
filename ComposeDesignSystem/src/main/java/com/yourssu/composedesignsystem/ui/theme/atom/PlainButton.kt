package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.base.NoRippleButton
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import com.yourssu.composedesignsystem.ui.theme.states.ButtonColorState
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import com.yourssu.composedesignsystem.ui.theme.util.alterColorIfPressed

data class PlainButtonState(
    private val text: String = "",
    @DrawableRes private val leftIcon: Int? = null,
    @DrawableRes private val rightIcon: Int? = null,
    private val isDisabled: Boolean = false,
    private val isWarned: Boolean = false,
    private val isPointed: Boolean = false,
    private val buttonSize: Size = Size.Medium
) {
    /**
     * [BoxButtonState]의 설명 참고
     */
    var textState by mutableStateOf(text)
    var leftIconState by mutableStateOf(leftIcon)
    var rightIconState by mutableStateOf(rightIcon)
    var isDisabledState by mutableStateOf(isDisabled)
    var isWarnedState by mutableStateOf(isWarned)
    var isPointedState by mutableStateOf(isPointed)
    var buttonSizeState by mutableStateOf(buttonSize)


    val colorState: ButtonColorState
        @Composable get() = ButtonColorState(
            contentColor = when {
                isWarnedState -> YdsTheme.colors.buttonWarned
                isPointedState -> YdsTheme.colors.buttonPoint
                else -> YdsTheme.colors.buttonNormal
            },
            disabledContentColor = YdsTheme.colors.buttonDisabled
        )

    private val sizeState: ButtonSizeState
        @Composable get() = plainButtonSizeStateBySize(size = buttonSizeState)
    val typo: TextStyle
        @Composable get() = sizeState.typo
    val iconSize: IconSize
        @Composable get() = sizeState.iconSize

    enum class Size {
        Large, Medium, Small
    }

    companion object {
        private const val textKey = "text"
        private const val leftIconKey = "leftIcon"
        private const val rightIconKey = "rightIcon"
        private const val disabledKey = "disabled"
        private const val warnedKey = "warned"
        private const val pointedKey = "pointed"
        private const val buttonSizeKey = "buttonSize"

        val Saver = run {
            mapSaver(
                save = {
                    mapOf(
                        textKey to it.textState,
                        leftIconKey to it.leftIconState,
                        rightIconKey to it.rightIconState,
                        disabledKey to it.isDisabledState,
                        warnedKey to it.isWarnedState,
                        pointedKey to it.isPointedState,
                        buttonSizeKey to it.buttonSizeState
                    )
                },
                restore = {
                    PlainButtonState(
                        it[textKey] as String,
                        it[leftIconKey] as Int?,
                        it[rightIconKey] as Int?,
                        it[disabledKey] as Boolean,
                        it[warnedKey] as Boolean,
                        it[pointedKey] as Boolean,
                        it[buttonSizeKey] as Size
                    )
                }
            )
        }
    }
}

@Composable
fun rememberPlainButtonState(
    text: String = "",
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    isPointed: Boolean = false,
    buttonSize: PlainButtonState.Size = PlainButtonState.Size.Medium
): PlainButtonState = rememberSaveable(
    text, leftIcon, rightIcon, isDisabled, isWarned, isPointed, buttonSize,
    saver = PlainButtonState.Saver
) {
    PlainButtonState(text, leftIcon, rightIcon, isDisabled, isWarned, isPointed, buttonSize)
}

@Composable
private fun plainButtonSizeStateBySize(
    size: PlainButtonState.Size
): ButtonSizeState = when (size) {
    PlainButtonState.Size.Large -> ButtonSizeState(iconSize = IconSize.Medium)
    PlainButtonState.Size.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button3,
        iconSize = IconSize.Small
    )
    PlainButtonState.Size.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
        iconSize = IconSize.ExtraSmall
    )
}

@Composable
fun PlainButton(
    onClick: () -> Unit,
    state: PlainButtonState,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    NoRippleButton(
        onClick = onClick,
        modifier = modifier,
        enabled = !state.isDisabledState,
        colors = state.colorState,
        elevation = elevation(0.dp, 0.dp, 0.dp),
        interactionSource = interactionSource,
        contentPadding = PaddingValues(0.dp)
    ) {
        if (state.buttonSizeState == PlainButtonState.Size.Large) {
            // leftIcon이 null이라면 rightIcon 할당
            val iconResource = state.leftIconState ?: state.rightIconState

            iconResource?.let { iconRes ->
                YdsIcon(
                    id = iconRes,
                    iconSize = state.iconSize
                )
            }
        } else {
            state.leftIconState?.let { leftIconId ->
                YdsIcon(
                    id = leftIconId,
                    iconSize = state.iconSize
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            Text(
                text = state.textState,
                style = state.typo
            )

            if (state.leftIconState == null) {
                state.rightIconState?.let { rightIconId ->
                    Spacer(modifier = Modifier.width(2.dp))
                    YdsIcon(
                        id = rightIconId,
                        iconSize = state.iconSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlainButtonPreview() {
    val buttonState1 = rememberPlainButtonState(
        text = "MEDIUM",
        leftIcon = R.drawable.ic_ground_filled,
        buttonSize = PlainButtonState.Size.Medium
    )
    val buttonState2 = rememberPlainButtonState(
        text = "click me!",
        leftIcon = R.drawable.ic_ground_filled,
        buttonSize = PlainButtonState.Size.Small,
        isWarned = true
    )

    YdsTheme {
        Column {
            PlainButton(
                onClick = {},
                state = buttonState1
            )
            PlainButton(
                onClick = {
                    buttonState1.textState = "Small"
                    buttonState1.isPointedState = true
                    buttonState1.buttonSizeState = PlainButtonState.Size.Small
                },
                state = buttonState2
            )
        }
    }
}