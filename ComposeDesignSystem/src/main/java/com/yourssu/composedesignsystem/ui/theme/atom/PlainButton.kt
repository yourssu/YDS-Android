package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import com.yourssu.composedesignsystem.ui.theme.util.toPressed

enum class PlainButtonSize {
    Large,
    Medium,
    Small
}

enum class PlainButtonStyle {
    Normal,
    Pointed,
    Disabled,
    Warned
}

data class PlainButtonState(
    val text: String = "",
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null,
    val buttonStyle: PlainButtonStyle = PlainButtonStyle.Normal,
    val buttonSize: PlainButtonSize = PlainButtonSize.Medium,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    private val isPressed: Boolean
        @Composable get() = interactionSource.collectIsPressedAsState().value

    val contentColor: Color
        @Composable get() = when (buttonStyle) {
            PlainButtonStyle.Normal -> YdsTheme.colors.buttonNormal.toPressed(isPressed)
            PlainButtonStyle.Pointed -> YdsTheme.colors.buttonPoint.toPressed(isPressed)
            PlainButtonStyle.Disabled -> YdsTheme.colors.buttonDisabled
            PlainButtonStyle.Warned -> YdsTheme.colors.buttonWarned.toPressed(isPressed)
        }

    val typo: TextStyle
        @Composable get() = when (buttonSize) {
            PlainButtonSize.Medium -> YdsTheme.typography.button3
            else -> YdsTheme.typography.button4
        }

    val iconSize: IconSize
        get() = when (buttonSize) {
            PlainButtonSize.Large -> IconSize.Medium
            PlainButtonSize.Medium -> IconSize.Small
            PlainButtonSize.Small -> IconSize.ExtraSmall
        }

    companion object {
        val Saver: Saver<PlainButtonState, *>
            get() = run {
                val textKey = "text"
                val leftIconKey = "leftIcon"
                val rightIconKey = "rightIcon"
                val buttonStyleKey = "buttonStyle"
                val buttonSizeKey = "buttonSize"

                mapSaver(
                    save = {
                        mapOf(
                            textKey to it.text,
                            leftIconKey to it.leftIcon,
                            rightIconKey to it.rightIcon,
                            buttonStyleKey to it.buttonStyle,
                            buttonSizeKey to it.buttonSize
                        )
                    },
                    restore = {
                        PlainButtonState(
                            it[textKey] as String,
                            it[leftIconKey] as Int?,
                            it[rightIconKey] as Int?,
                            it[buttonStyleKey] as PlainButtonStyle,
                            it[buttonSizeKey] as PlainButtonSize
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
    buttonStyle: PlainButtonStyle = PlainButtonStyle.Normal,
    buttonSize: PlainButtonSize = PlainButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) = rememberSaveable(
    text, leftIcon, rightIcon, buttonStyle, buttonSize, interactionSource,
    stateSaver = PlainButtonState.Saver
) {
    mutableStateOf(
        PlainButtonState(text, leftIcon, rightIcon, buttonStyle, buttonSize, interactionSource)
    )
}

@Composable
fun PlainButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonState: PlainButtonState,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = textButtonColors(contentColor = buttonState.contentColor),
        elevation = elevation(0.dp, 0.dp, 0.dp)
    ) {
        if (buttonState.buttonSize == PlainButtonSize.Large) {

            // leftIcon이 null이라면 rightIcon 할당
            val iconResource = buttonState.leftIcon ?: buttonState.rightIcon

            iconResource?.let { iconRes ->
                YdsIcon(
                    id = iconRes,
                    iconSize = buttonState.iconSize,
                    tint = buttonState.contentColor
                )
            }
        } else {
            if (buttonState.leftIcon != null) {
                YdsIcon(
                    id = buttonState.leftIcon,
                    iconSize = buttonState.iconSize,
                    tint = buttonState.contentColor,
                    modifier = Modifier.padding(end = 2.dp)
                )
            }

            Text(
                text = buttonState.text,
                style = buttonState.typo
            )

            if (buttonState.leftIcon == null && buttonState.rightIcon != null) {
                YdsIcon(
                    id = buttonState.rightIcon,
                    iconSize = buttonState.iconSize,
                    tint = buttonState.contentColor,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlainButtonPreview() {
    val buttonState1 by rememberPlainButtonState(
        text = "MEDIUM",
        buttonSize = PlainButtonSize.Medium,
        buttonStyle = PlainButtonStyle.Warned,
        leftIcon = R.drawable.ic_ground_filled
    )
    val buttonState2 by rememberPlainButtonState(
        buttonSize = PlainButtonSize.Large,
        buttonStyle = PlainButtonStyle.Normal,
        leftIcon = R.drawable.ic_ground_filled
    )

    Column {
        PlainButton(
            onClick = {},
            buttonState = buttonState1
        )
        PlainButton(
            onClick = {},
            buttonState = buttonState2
        )
    }
}