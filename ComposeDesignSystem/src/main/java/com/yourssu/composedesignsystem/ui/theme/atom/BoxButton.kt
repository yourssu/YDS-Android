package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsTypo
import com.yourssu.composedesignsystem.ui.theme.rule.YdsBorder
import com.yourssu.composedesignsystem.ui.theme.rule.YdsRounding
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import com.yourssu.composedesignsystem.ui.theme.states.ButtonColorState
import com.yourssu.composedesignsystem.ui.theme.util.maybePressed

// TODO: BoxButtonStyle로 이름 바꾸고, isPressed 값 외부에서 주입받기
data class BoxButtonState(
    val isDisabled: Boolean = false,
    val isWarned: Boolean = false,
    val buttonType: Type = Type.Filled,
    val buttonSize: Size = Size.ExtraLarge,
    val roundingShape: CornerBasedShape = YdsRounding.large,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    private val isPressed: Boolean
        @Composable get() = interactionSource.collectIsPressedAsState().value

    private val colorState: ButtonColorState
        @Composable get() = boxButtonColorByType(type = buttonType)

    val contentColor: Color
        @Composable get() = when {
            isDisabled -> colorState.disabledContentColor
            isWarned -> colorState.warnedContentColor
            else -> colorState.contentColor
        }.maybePressed(isPressed = isPressed)

    val disabledContentColor: Color
        @Composable get() = colorState.disabledContentColor

    val backgroundColor: Color
        @Composable get() = when {
            isDisabled -> colorState.disabledBgColor
            isWarned -> colorState.warnedBgColor
            else -> colorState.bgColor
        }.maybePressed(isPressed = isPressed)

    val disabledBackgroundColor: Color
        @Composable get() = colorState.disabledBgColor

    private val sizeState: ButtonSizeState = boxButtonSizeStateBySize(size = buttonSize)

    val typo: TextStyle = sizeState.typo
    val iconSize: IconSize = sizeState.iconSize
    val height: Dp = sizeState.height
    val horizontalPadding: Dp = sizeState.horizontalPadding

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

    companion object {
        val Saver = run {
            val disabledKey = "disabled"
            val warnedKey = "warned"
            val buttonTypeKey = "buttonType"
            val buttonSizeKey = "buttonSize"
            val roundingKey = "rounding"

            mapSaver(
                save = {
                    mapOf(
                        disabledKey to it.isDisabled,
                        warnedKey to it.isWarned,
                        buttonTypeKey to it.buttonType,
                        buttonSizeKey to it.buttonSize,
                        roundingKey to it.roundingShape
                    )
                },
                restore = {
                    BoxButtonState(
                        it[disabledKey] as Boolean,
                        it[warnedKey] as Boolean,
                        it[buttonTypeKey] as Type,
                        it[buttonSizeKey] as Size,
                        it[roundingKey] as CornerBasedShape
                    )
                }
            )
        }
    }
}

// TODO: 확인해보기
@Composable
private fun boxButtonColorByType(
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

private fun boxButtonSizeStateBySize(
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

@Composable
fun BoxButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    boxButtonState: BoxButtonState
) {
    val buttonColors = buttonColors(
        backgroundColor = boxButtonState.backgroundColor,
        contentColor = boxButtonState.contentColor,
        disabledBackgroundColor = boxButtonState.disabledBackgroundColor,
        disabledContentColor = boxButtonState.disabledContentColor
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .height(boxButtonState.height),
        enabled = !boxButtonState.isDisabled,
        elevation = elevation(0.dp, 0.dp, 0.dp),
        colors = buttonColors,
        border = if (boxButtonState.buttonType == BoxButtonState.Type.Line) {
            BorderStroke(YdsBorder.normal, boxButtonState.contentColor)
        } else { null },
        interactionSource = boxButtonState.interactionSource,
        shape = boxButtonState.roundingShape,
        contentPadding = PaddingValues(
            horizontal = boxButtonState.horizontalPadding
        )
    ) {
        leftIcon?.let { leftIconId ->
                YdsIcon(
                    id = leftIconId,
                    iconSize = boxButtonState.iconSize,
                    tint = boxButtonState.contentColor
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

        Text(
            text = text,
            style = boxButtonState.typo
        )

        rightIcon?.let { rightIconId ->
            Spacer(modifier = Modifier.width(4.dp))
            YdsIcon(
                id = rightIconId,
                iconSize = boxButtonState.iconSize,
                tint = boxButtonState.contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxButtonPreview() {
    val buttonState1 by remember {
        mutableStateOf(
            BoxButtonState(
                buttonType = BoxButtonState.Type.Filled
            )
        )
    }
    YdsTheme {
        Column {
            BoxButton(
                onClick = { },
                text = "Filled",
                boxButtonState = buttonState1,
                leftIcon = R.drawable.ic_ground_filled
            )
        }
    }
}