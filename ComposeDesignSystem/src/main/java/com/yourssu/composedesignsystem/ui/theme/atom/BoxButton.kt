package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsTypo
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import com.yourssu.composedesignsystem.ui.theme.states.ButtonColorState

data class BoxButtonState(
    val text: String = "",
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null,
    val isDisabled: Boolean = false,
    val isWarned: Boolean = false,
    val buttonType: Type = Type.Filled,
    val buttonSize: Size = Size.ExtraLarge,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    enum class Type {
        Filled,
        Tinted,
        Line
    }

    enum class Size {
        ExtraLarge,
        Large,
        Medium,
        Small
    }
}

@Composable
fun boxButtonColorByType(
    type: BoxButtonState.Type
): ButtonColorState = when (type) {
    BoxButtonState.Type.Filled -> ButtonColorState(
        contentColor = YdsTheme.colors.buttonBright,
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        warnedContentColor = YdsTheme.colors.buttonBright,
        bgColor = YdsTheme.colors.buttonPoint,
        disabledBgColor = YdsTheme.colors.buttonDisabledBG,
        warnedBgColor = YdsTheme.colors.buttonWarned
    )
    BoxButtonState.Type.Tinted -> ButtonColorState(
        contentColor = YdsTheme.colors.buttonPoint,
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        warnedContentColor = YdsTheme.colors.buttonWarned,
        bgColor = YdsTheme.colors.buttonPointBG,
        disabledBgColor = YdsTheme.colors.buttonDisabledBG,
        warnedBgColor = YdsTheme.colors.buttonWarnedBG
    )
    BoxButtonState.Type.Line -> ButtonColorState(
        contentColor = YdsTheme.colors.buttonPoint,
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        warnedContentColor = YdsTheme.colors.buttonWarned,
        bgColor = YdsTheme.colors.bgNormal
    )
}

fun boxButtonSizeStateBySize(
    size: BoxButtonState.Size
): ButtonSizeState = when (size) {
    BoxButtonState.Size.ExtraLarge -> ButtonSizeState(
        typo = YdsTypo.button1,
        iconSize = IconSize.Medium,
        height = 56.dp,
        horizontalPadding = 16.dp
    )
    BoxButtonState.Size.Large -> ButtonSizeState(
        typo = YdsTypo.button2,
        iconSize = IconSize.Medium,
        height = 48.dp,
        horizontalPadding = 16.dp
    )
    BoxButtonState.Size.Medium -> ButtonSizeState(
        typo = YdsTypo.button2,
        iconSize = IconSize.Medium,
        height = 40.dp,
        horizontalPadding = 12.dp
    )
    BoxButtonState.Size.Small -> ButtonSizeState(
        typo = YdsTypo.button4,
        iconSize = IconSize.Small,
        height = 32.dp,
        horizontalPadding = 12.dp
    )
}