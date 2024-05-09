package com.yourssu.design.system.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.YdsBaseButton
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.states.ButtonColorState
import com.yourssu.design.system.compose.states.ButtonSizeState

enum class BoxButtonType {
    Filled,
    Tinted,
    Line,
    CustomColorFilled,
    CustomColorLine,
}

enum class BoxButtonSize {
    ExtraLarge,
    Large,
    Medium,
    Small,
}

@Composable
private fun boxButtonColorByType(
    isWarned: Boolean,
    type: BoxButtonType,
    customColor: Color,
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
        disabledBgColor = YdsTheme.colors.buttonDisabledBG,
    )

    BoxButtonType.Line -> ButtonColorState(
        contentColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> YdsTheme.colors.buttonPoint
        },
        disabledContentColor = YdsTheme.colors.buttonDisabled,
    )

    BoxButtonType.CustomColorFilled -> ButtonColorState(
        contentColor = YdsTheme.colors.buttonBright,
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        bgColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> customColor
        },
        disabledBgColor = YdsTheme.colors.buttonDisabledBG,
    )

    BoxButtonType.CustomColorLine -> ButtonColorState(
        contentColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> customColor
        },
        disabledContentColor = YdsTheme.colors.buttonDisabled,
    )
}

@Composable
private fun boxButtonSizeStateBySize(
    size: BoxButtonSize,
): ButtonSizeState = when (size) {
    BoxButtonSize.ExtraLarge -> ButtonSizeState(
        typo = YdsTheme.typography.button1,
        iconSize = IconSize.Medium,
        height = 56.dp,
        horizontalPadding = 16.dp,
    )

    BoxButtonSize.Large -> ButtonSizeState(
        typo = YdsTheme.typography.button2,
        iconSize = IconSize.Medium,
        height = 48.dp,
        horizontalPadding = 16.dp,
    )

    BoxButtonSize.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button2,
        iconSize = IconSize.Medium,
        height = 40.dp,
        horizontalPadding = 12.dp,
    )

    BoxButtonSize.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
        iconSize = IconSize.Small,
        height = 32.dp,
        horizontalPadding = 12.dp,
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
    rounding: Dp = 8.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    customBoxColor: Color = YdsTheme.colors.buttonPoint,
) {
    val roundingDp = when (sizeType) {
        BoxButtonSize.ExtraLarge -> 8.dp
        BoxButtonSize.Large -> rounding
        BoxButtonSize.Medium, BoxButtonSize.Small -> 4.dp
    }
    val (typo, iconSize, height, horizontalPadding) = boxButtonSizeStateBySize(size = sizeType)

    YdsBaseButton(
        onClick = onClick,
        colors = boxButtonColorByType(isWarned = isWarned, type = buttonType, customColor = customBoxColor),
        modifier = Modifier
            .then(modifier)
            .height(height),
        enabled = !isDisabled,
        showBorder = (buttonType == BoxButtonType.Line || buttonType == BoxButtonType.CustomColorLine),
        interactionSource = interactionSource,
        rounding = roundingDp,
        contentPadding = PaddingValues(
            horizontal = horizontalPadding,
        ),
    ) {
        leftIcon?.let { icon ->
            Icon(
                id = icon,
                iconSize = iconSize,
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        YdsText(
            text = text,
            style = typo,
        )

        // leftIcon이 null일 때만
        leftIcon ?: rightIcon?.let { icon ->
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                id = icon,
                iconSize = iconSize,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BoxButtonPreview() {
    var text by remember { mutableStateOf("Default") }

    YdsTheme {
        Column {
//            BoxButton(
//                onClick = { /*TODO*/ },
//                text = text,
//                rightIcon = R.drawable.ic_ground_filled,
//                buttonType = BoxButtonType.Line,
//                sizeType = BoxButtonSize.Small,
//            )
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
                rightIcon = R.drawable.ic_ground_filled,
                isWarned = true,
            )

            // 기본
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
            )

            // 커스텀 색 (기존 Fill + custom색상 -> 기존 fill이면 custom 색상 적용 안 됨)
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
                customBoxColor = YdsTheme.colors.logoLightBlue,
            )

            // 커스텀 색 지정 (buttonType이 CustomColorFilled이면 custom 색상 적용)
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
                customBoxColor = YdsTheme.colors.logoLightBlue,
                buttonType = BoxButtonType.CustomColorFilled,
            )
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
                buttonType = BoxButtonType.Line,
            )
            BoxButton(
                onClick = { text += "+" },
                text = "BoxButton 2",
                leftIcon = R.drawable.ic_ground_filled,
                buttonType = BoxButtonType.CustomColorLine,
                customBoxColor = YdsTheme.colors.logoLightBlue,
            )
        }
    }
}
