package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsTypography
import com.yourssu.composedesignsystem.ui.theme.util.toPressed

enum class PlainButtonSize {
    LARGE,
    MEDIUM,
    SMALL
}

enum class PlainButtonState {
    NORMAL,
    POINTED,
    DISABLED,
    WARNED
}

enum class IconSize(val dp: Dp) {
    MEDIUM(24.dp),
    SMALL(20.dp),
    EXTRA_SMALL(16.dp)
}

data class PlainButtonStateStore(
    val text: String = "",
    val size: PlainButtonSize = PlainButtonSize.MEDIUM,
    val buttonState: PlainButtonState = PlainButtonState.NORMAL,
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    private val isPressed: Boolean
        @Composable
        get() = interactionSource.collectIsPressedAsState().value

    val contentColor: Color
        @Composable
        get() = when (buttonState) {
            PlainButtonState.NORMAL -> YdsTheme.colors.buttonNormal.toPressed(isPressed)
            PlainButtonState.POINTED -> YdsTheme.colors.buttonPoint.toPressed(isPressed)
            PlainButtonState.DISABLED -> YdsTheme.colors.buttonDisabled
            PlainButtonState.WARNED -> YdsTheme.colors.buttonWarned.toPressed(isPressed)
        }

    val iconSize: IconSize
        get() = when (size) {
            PlainButtonSize.LARGE -> IconSize.MEDIUM
            PlainButtonSize.MEDIUM -> IconSize.SMALL
            PlainButtonSize.SMALL -> IconSize.EXTRA_SMALL
        }

    val typo: TextStyle
        @Composable
        get() = when (size) {
            PlainButtonSize.MEDIUM -> YdsTheme.typography.button3
            else -> YdsTheme.typography.button4
        }
}

@Composable
fun PlainButton(
    buttonStateStore: PlainButtonStateStore,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = textButtonColors(contentColor = buttonStateStore.contentColor),
        elevation = elevation(0.dp, 0.dp, 0.dp)
    ) {
        if (buttonStateStore.size == PlainButtonSize.LARGE) {
            // leftIcon이 null이라면 rightIcon 할당
            val iconResource = buttonStateStore.leftIcon ?: buttonStateStore.rightIcon

            iconResource?.let { iconRes ->
                Icon(
                    imageVector = ImageVector.vectorResource(iconRes),
                    contentDescription = "$iconRes",
                    tint = buttonStateStore.contentColor,
                    modifier = Modifier.size(
                        buttonStateStore.iconSize.dp
                    )
                )
            }
        } else {
            if (buttonStateStore.leftIcon != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(buttonStateStore.leftIcon),
                    contentDescription = "${buttonStateStore.leftIcon}",
                    tint = buttonStateStore.contentColor,
                    modifier = Modifier
                        .size(
                            buttonStateStore.iconSize.dp
                        )
                        .padding(end = 2.dp)
                )
            }

            Text(
                text = buttonStateStore.text,
                style = buttonStateStore.typo
            )

            if (buttonStateStore.leftIcon == null && buttonStateStore.rightIcon != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(buttonStateStore.rightIcon),
                    contentDescription = "${buttonStateStore.rightIcon}",
                    tint = buttonStateStore.contentColor,
                    modifier = Modifier
                        .size(
                            buttonStateStore.iconSize.dp
                        )
                        .padding(start = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlainButtonPreview() {
    val plainButtonState1 = remember {
        PlainButtonStateStore(
            size = PlainButtonSize.LARGE,
            leftIcon = R.drawable.ic_ground_filled,
            buttonState = PlainButtonState.NORMAL
        )
    }

    val plainButtonState2 = remember {
        PlainButtonStateStore(
            text = "Medium",
            size = PlainButtonSize.MEDIUM,
            leftIcon = R.drawable.ic_ground_filled,
            buttonState = PlainButtonState.NORMAL
        )
    }

    Column {
        PlainButton(
            plainButtonState1
        )
        PlainButton(
            plainButtonState2
        )
    }
}