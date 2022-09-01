package com.yourssu.composedesignsystem.ui.theme.atom

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon

enum class Type {
    FILLED,
    TINTED,
    LINE
}

enum class Size {
    EXTRA_LARGE,
    LARGE,
    MEDIUM,
    SMALL
}

enum class Rounding(val dp: Dp) {
    FOUR(4.dp),
    EIGHT(8.dp)
}

enum class ButtonState {
    IS_DISABLED,
    IS_ENABLED,
    IS_WARNED
}

data class BoxButtonState(
    val text: String = "",
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null,
    val type: Type = Type.FILLED,
    val buttonState: ButtonState = ButtonState.IS_ENABLED,
    val sizeState: Size = Size.EXTRA_LARGE,
    val roundingState: Rounding = Rounding.EIGHT,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    private val isPressed
        @Composable get() = interactionSource.collectIsPressedAsState().value

    val isEnabled = buttonState != ButtonState.IS_DISABLED
    val isLine = type == Type.LINE
    val rounding = roundingState.dp

    val height = when (sizeState) {
        Size.EXTRA_LARGE -> 56.dp
        Size.LARGE -> 48.dp
        Size.MEDIUM -> 40.dp
        Size.SMALL -> 32.dp
    }

    val typo
        @Composable get() = when (sizeState) {
            Size.EXTRA_LARGE -> YdsTheme.typography.button1
            Size.LARGE, Size.MEDIUM -> YdsTheme.typography.button2
            Size.SMALL -> YdsTheme.typography.button4
        }

    val iconSize
        get() = when (sizeState) {
            Size.EXTRA_LARGE, Size.LARGE, Size.MEDIUM -> IconSize.Medium
            Size.SMALL -> IconSize.Small
        }

    val horizontalPadding
        get() = when (sizeState) {
            Size.EXTRA_LARGE, Size.LARGE -> 16.dp
            Size.MEDIUM, Size.SMALL -> 12.dp
        }

    val backgroundColor
        @Composable get() = when (type) {
            Type.FILLED -> when (buttonState) {
                ButtonState.IS_ENABLED -> YdsTheme.colors.buttonPoint.toPressed()
                ButtonState.IS_DISABLED -> YdsTheme.colors.buttonPoint
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarned.toPressed()
            }
            Type.TINTED -> when (buttonState) {
                ButtonState.IS_ENABLED -> YdsTheme.colors.buttonPointBG
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarnedBG
                ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabledBG
            }
            Type.LINE -> YdsTheme.colors.bgNormal
        }

    val contentColor
        @Composable get() = when (type) {
            Type.FILLED -> YdsTheme.colors.buttonBright
            Type.TINTED -> when (buttonState) {
                ButtonState.IS_ENABLED -> YdsTheme.colors.buttonPoint.toPressed()
                ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabled
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarned.toPressed()
            }
            Type.LINE -> when (buttonState) {
                ButtonState.IS_ENABLED -> YdsTheme.colors.buttonPoint.toPressed()
                ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabled
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarned.toPressed()
            }
        }

    val disabledBackgroundColor
        @Composable get() = when (type) {
            Type.FILLED, Type.TINTED -> when (buttonState) {
                ButtonState.IS_ENABLED -> YdsTheme.colors.buttonDisabledBG
                ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabledBG
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarned.toPressed()
            }
            Type.LINE -> YdsTheme.colors.bgNormal
        }

    val disabledContentColor
        @Composable get() = when (type) {
            Type.FILLED -> when (buttonState) {
                ButtonState.IS_ENABLED, ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabled
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonBright
            }
            Type.TINTED -> when (buttonState) {
                ButtonState.IS_ENABLED, ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabled
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarned.toPressed()
            }
            Type.LINE -> when (buttonState) {
                ButtonState.IS_ENABLED -> YdsTheme.colors.buttonPoint.toPressed()
                ButtonState.IS_DISABLED -> YdsTheme.colors.buttonDisabled
                ButtonState.IS_WARNED -> YdsTheme.colors.buttonWarned.toPressed()
            }
        }

    @Composable
    fun Color.toPressed(): Color {
        return if(isPressed) {
            when(this) {
                YdsTheme.colors.buttonPoint -> YdsTheme.colors.buttonPointPressed
                YdsTheme.colors.buttonWarned -> YdsTheme.colors.buttonWarnedPressed
                YdsTheme.colors.buttonNormal -> YdsTheme.colors.buttonNormalPressed
                else -> this
            }
        } else {
            this
        }
    }
}

@Composable
fun BoxButton(
    boxButtonState: BoxButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .then(modifier)
            .height(boxButtonState.height)
            .padding(horizontal = boxButtonState.horizontalPadding),
        enabled = boxButtonState.isEnabled,
        elevation = elevation(0.dp, 0.dp, 0.dp),
        interactionSource = boxButtonState.interactionSource,
        colors = buttonColors(
            backgroundColor = boxButtonState.backgroundColor,
            contentColor = boxButtonState.contentColor,
            disabledBackgroundColor = boxButtonState.disabledBackgroundColor,
            disabledContentColor = boxButtonState.disabledContentColor
        ),
        border = if (boxButtonState.isLine) {
            BorderStroke(
                1.dp,
                boxButtonState.contentColor
            )
        } else {
            null
        },
        shape = RoundedCornerShape(boxButtonState.rounding)
    ) {
        if (boxButtonState.leftIcon != null) {
            YdsIcon(
                id = boxButtonState.leftIcon,
                iconSize = boxButtonState.iconSize,
                tint = boxButtonState.contentColor
            )
            Spacer(
                modifier = Modifier.padding(
                    end = 4.dp
                )
            )
        }
        Text(
            text = boxButtonState.text,
            style = boxButtonState.typo
        )
        if (boxButtonState.leftIcon == null && boxButtonState.rightIcon != null) {
            Spacer(
                modifier = Modifier.padding(
                    end = 4.dp
                )
            )
            YdsIcon(
                id = boxButtonState.rightIcon,
                iconSize = boxButtonState.iconSize,
                tint = boxButtonState.contentColor,
            )
        }
    }
}

val boxButtonSaver = run {
    val textKey = "text"
    val leftIconKey = "leftIcon"
    val rightIconKey = "rightIcon"
    val typeKey = "type"
    val buttonStateKey = "buttonState"
    val sizeStateKey = "sizeState"
    val roundingStateKey = "roundingState"

    mapSaver(
        save = {
            mapOf(
                textKey to it.text,
                leftIconKey to it.leftIcon,
                rightIconKey to it.rightIcon,
                typeKey to it.type,
                buttonStateKey to it.buttonState,
                sizeStateKey to it.sizeState,
                roundingStateKey to it.roundingState
            )
        },
        restore = {
            BoxButtonState(
                it[textKey] as String,
                it[leftIconKey] as Int?,
                it[rightIconKey] as Int?,
                it[typeKey] as Type,
                it[buttonStateKey] as ButtonState,
                it[sizeStateKey] as Size,
                it[roundingStateKey] as Rounding
            )
        }
    )
}

@Preview
@Composable
fun PreviewBoxButton() {
    val context = LocalContext.current

    fun onClick() {
        Toast.makeText(
            context,
            "Click!",
            Toast.LENGTH_SHORT
        ).show()
    }

    var state1 by rememberSaveable(stateSaver = boxButtonSaver) {
        mutableStateOf(
            BoxButtonState(
                text = "FILLED",
                type = Type.FILLED,
                leftIcon = R.drawable.ic_ground_filled,
                buttonState = ButtonState.IS_ENABLED,
                sizeState = Size.EXTRA_LARGE,
                roundingState = Rounding.EIGHT
            )
        )
    }

    var state2 by rememberSaveable(stateSaver = boxButtonSaver) {
        mutableStateOf(
            BoxButtonState(
                text = "LINE",
                type = Type.LINE,
                leftIcon = R.drawable.ic_ground_filled,
                buttonState = ButtonState.IS_ENABLED,
                sizeState = Size.EXTRA_LARGE,
                roundingState = Rounding.FOUR
            )
        )
    }

    YdsTheme {
        var count by rememberSaveable { mutableStateOf(0) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
        ) {
            BoxButton(
                boxButtonState = state1,
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))

            BoxButton(
                boxButtonState = state2,
                onClick = {
                    count++
                    state1 = if (count % 2 == 1) {
                        state1.copy(text = "test", buttonState = ButtonState.IS_WARNED)
                    } else {
                        state1.copy(text = "FILLED", buttonState = ButtonState.IS_ENABLED)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}