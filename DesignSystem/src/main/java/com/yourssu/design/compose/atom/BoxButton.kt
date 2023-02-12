package com.yourssu.design.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.R
import com.yourssu.design.compose.YdsTheme
import com.yourssu.design.compose.base.NoRippleButton
import com.yourssu.design.compose.foundation.IconSize
import com.yourssu.design.compose.foundation.YdsIcon
import com.yourssu.design.compose.states.ButtonSizeState
import com.yourssu.design.compose.states.ButtonColorState

enum class BoxButtonType {
    Filled,
    Tinted,
    Line
}

enum class BoxButtonSize {
    ExtraLarge,
    Large,
    Medium,
    Small
}

@Composable
private fun boxButtonColorByType(
    isWarned: Boolean,
    type: BoxButtonType
): ButtonColorState = when (type) {
    BoxButtonType.Filled -> ButtonColorState(
        contentColor = YdsTheme.colors.buttonBright,
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        bgColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> YdsTheme.colors.buttonPoint
        },
        disabledBgColor = YdsTheme.colors.buttonDisabledBG,
    )
    BoxButtonType.Tinted -> ButtonColorState(
        contentColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> YdsTheme.colors.buttonPoint
        },
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        bgColor = when {
            isWarned -> YdsTheme.colors.buttonWarnedBG
            else -> YdsTheme.colors.buttonPointBG
        },
        disabledBgColor = YdsTheme.colors.buttonDisabledBG
    )
    BoxButtonType.Line -> ButtonColorState(
        contentColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> YdsTheme.colors.buttonPoint
        },
        disabledContentColor = YdsTheme.colors.buttonDisabled
    )
}

@Composable
private fun boxButtonSizeStateBySize(
    size: BoxButtonSize
): ButtonSizeState = when (size) {
    BoxButtonSize.ExtraLarge -> ButtonSizeState(
        typo = YdsTheme.typography.button1,
        iconSize = IconSize.Medium,
        height = 56.dp,
        horizontalPadding = 16.dp
    )
    BoxButtonSize.Large -> ButtonSizeState(
        typo = YdsTheme.typography.button2,
        iconSize = IconSize.Medium,
        height = 48.dp,
        horizontalPadding = 16.dp
    )
    BoxButtonSize.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button2,
        iconSize = IconSize.Medium,
        height = 40.dp,
        horizontalPadding = 12.dp
    )
    BoxButtonSize.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
        iconSize = IconSize.Small,
        height = 32.dp,
        horizontalPadding = 12.dp
    )
}

@Composable
fun BoxButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    sizeType: BoxButtonSize = BoxButtonSize.Large,
    buttonType: BoxButtonType = BoxButtonType.Filled,
    rounding: CornerBasedShape = YdsTheme.rounding.large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val roundingShape = when (sizeType) {
        BoxButtonSize.ExtraLarge -> YdsTheme.rounding.large
        BoxButtonSize.Large -> rounding
        BoxButtonSize.Medium, BoxButtonSize.Small -> YdsTheme.rounding.medium
    }
    val (typo, iconSize, height, horizontalPadding) = boxButtonSizeStateBySize(size = sizeType)

    NoRippleButton(
        onClick = onClick,
        colors = boxButtonColorByType(isWarned = isWarned, type = buttonType),
        modifier = Modifier
            .then(modifier)
            .height(height),
        enabled = !isDisabled,
        showBorder = (buttonType == BoxButtonType.Line),
        elevation = elevation(0.dp, 0.dp, 0.dp),
        interactionSource = interactionSource,
        shape = roundingShape,
        contentPadding = PaddingValues(
            horizontal = horizontalPadding
        )
    ) {
        leftIcon?.let { icon ->
            YdsIcon(
                id = icon,
                iconSize = iconSize
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        Text(
            text = text,
            style = typo
        )

        // leftIcon이 null일 때만
        leftIcon ?: rightIcon?.let { icon ->
            Spacer(modifier = Modifier.width(4.dp))
            YdsIcon(
                id = icon,
                iconSize = iconSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxButtonPreview() {
    var text by remember { mutableStateOf("Default") }

    YdsTheme {
        Column {
            BoxButton(
                onClick = { /*TODO*/ },
                text = text,
                rightIcon = R.drawable.ic_ground_filled,
                buttonType = BoxButtonType.Line,
                sizeType = BoxButtonSize.Small
            )
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
                rightIcon = R.drawable.ic_ground_filled,
                isWarned = true
            )
        }
    }
}