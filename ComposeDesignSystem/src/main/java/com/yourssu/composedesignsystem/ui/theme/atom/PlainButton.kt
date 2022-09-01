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

sealed class PlainButtonSize(val iconSize: IconSize) {
    open val typo: TextStyle
        @Composable get() = YdsTheme.typography.button1

    object Large : PlainButtonSize(IconSize.Medium)
    object Medium : PlainButtonSize(IconSize.Small) {
        override val typo: TextStyle
            @Composable get() = YdsTheme.typography.button3
    }
    object Small : PlainButtonSize(IconSize.ExtraSmall) {
        override val typo: TextStyle
            @Composable get() = YdsTheme.typography.button4
    }
}

// TODO: sealed class 로 변환하기
enum class PlainButtonType {
    Normal,
    Pointed,
    Disabled,
    Warned
}

data class PlainButtonState(
    val text: String = "",
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null,
    val buttonType: PlainButtonType = PlainButtonType.Normal,
    val size: PlainButtonSize = PlainButtonSize.Medium,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    private val isPressed: Boolean
        @Composable get() = interactionSource.collectIsPressedAsState().value

    val contentColor: Color
        @Composable get() = when (buttonType) {
            PlainButtonType.Normal -> YdsTheme.colors.buttonNormal.toPressed(isPressed)
            PlainButtonType.Pointed -> YdsTheme.colors.buttonPoint.toPressed(isPressed)
            PlainButtonType.Disabled -> YdsTheme.colors.buttonDisabled
            PlainButtonType.Warned -> YdsTheme.colors.buttonWarned.toPressed(isPressed)
        }

//    val typo: TextStyle
//        @Composable get() = when (buttonSize) {
//            PlainButtonSize.Medium -> YdsTheme.typography.button3
//            else -> YdsTheme.typography.button4
//        }
//
//    val iconSize: IconSize
//        get() = when (buttonSize) {
//            PlainButtonSize.Large -> IconSize.Medium
//            PlainButtonSize.Medium -> IconSize.Small
//            PlainButtonSize.Small -> IconSize.ExtraSmall
//        }

    companion object {
        val Saver: Saver<PlainButtonState, *>
            get() = run {
                val textKey = "text"
                val leftIconKey = "leftIcon"
                val rightIconKey = "rightIcon"
                val buttonTypeKey = "buttonType"
                val buttonSizeKey = "buttonSize"

                mapSaver(
                    save = {
                        mapOf(
                            textKey to it.text,
                            leftIconKey to it.leftIcon,
                            rightIconKey to it.rightIcon,
                            buttonTypeKey to it.buttonType,
                            buttonSizeKey to it.size
                        )
                    },
                    restore = {
                        PlainButtonState(
                            it[textKey] as String,
                            it[leftIconKey] as Int?,
                            it[rightIconKey] as Int?,
                            it[buttonTypeKey] as PlainButtonType,
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
    buttonType: PlainButtonType = PlainButtonType.Normal,
    buttonSize: PlainButtonSize = PlainButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) = rememberSaveable(
    text, leftIcon, rightIcon, buttonType, buttonSize, interactionSource,
    stateSaver = PlainButtonState.Saver
) {
    mutableStateOf(
        PlainButtonState(text, leftIcon, rightIcon, buttonType, buttonSize, interactionSource)
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
        if (buttonState.size == PlainButtonSize.Large) {

            // leftIcon이 null이라면 rightIcon 할당
            val iconResource = buttonState.leftIcon ?: buttonState.rightIcon

            iconResource?.let { iconRes ->
                YdsIcon(
                    id = iconRes,
                    iconSize = buttonState.size.iconSize,
                    tint = buttonState.contentColor
                )
            }
        } else {
            if (buttonState.leftIcon != null) {
                YdsIcon(
                    id = buttonState.leftIcon,
                    iconSize = buttonState.size.iconSize,
                    tint = buttonState.contentColor,
                    modifier = Modifier.padding(end = 2.dp)
                )
            }
            (buttonState as PlainButtonSize.Medium)
            (buttonState as PlainButtonSize.Small)
            Text(
                text = buttonState.text,
                style = buttonState.size.typo
            )

            if (buttonState.leftIcon == null && buttonState.rightIcon != null) {
                YdsIcon(
                    id = buttonState.rightIcon,
                    iconSize = buttonState.size.iconSize,
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
        buttonType = PlainButtonType.Warned,
        leftIcon = R.drawable.ic_ground_filled
    )
    val buttonState2 by rememberPlainButtonState(
        buttonSize = PlainButtonSize.Large,
        buttonType = PlainButtonType.Normal,
        leftIcon = R.drawable.ic_ground_filled
    )

    Column {
        PlainButton(
            onClick = {},
            buttonState = buttonState1,
            modifier = Modifier.padding(0.dp)
        )
        PlainButton(
            onClick = {},
            buttonState = buttonState2
        )
    }
}