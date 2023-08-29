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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.Text
import com.yourssu.design.system.compose.base.YdsBaseButton
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.rule.YdsRounding
import com.yourssu.design.system.compose.states.ButtonColorState
import com.yourssu.design.system.compose.states.ButtonSizeState

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
    rounding: YdsRounding = YdsRounding.Large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val rounding = when (sizeType) {
        BoxButtonSize.ExtraLarge -> YdsRounding.Large
        BoxButtonSize.Large -> rounding
        BoxButtonSize.Medium, BoxButtonSize.Small -> YdsRounding.Medium
    }
    val (typo, iconSize, height, horizontalPadding) = boxButtonSizeStateBySize(size = sizeType)

    YdsBaseButton(
        onClick = onClick,
        colors = boxButtonColorByType(isWarned = isWarned, type = buttonType),
        modifier = Modifier
            .then(modifier)
            .height(height),
        enabled = !isDisabled,
        showBorder = (buttonType == BoxButtonType.Line),
        interactionSource = interactionSource,
        shape = rounding.shape,
        contentPadding = PaddingValues(
            horizontal = horizontalPadding
        )
    ) {
        leftIcon?.let { icon ->
            Icon(
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
            Icon(
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