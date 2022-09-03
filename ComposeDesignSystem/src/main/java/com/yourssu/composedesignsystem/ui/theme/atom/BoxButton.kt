package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
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

// TODO: isPressed 값 외부에서 주입받기
// copy로 직접 할당하는 방법 말고 다른 방법으로도 recomposition 일어날 수 있게 설계하기
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

            // rounding 넣으면 오류남,
            mapSaver(
                save = {
                    mapOf(
                        disabledKey to it.isDisabled,
                        warnedKey to it.isWarned,
                        buttonTypeKey to it.buttonType,
                        buttonSizeKey to it.buttonSize,
                        // roundingKey to it.roundingShape
                    )
                },
                restore = {
                    BoxButtonState(
                        it[disabledKey] as Boolean,
                        it[warnedKey] as Boolean,
                        it[buttonTypeKey] as Type,
                        it[buttonSizeKey] as Size,
                        // it[roundingKey] as CornerBasedShape
                    )
                }
            )
        }
    }
}

@Composable
fun rememberBoxButtonStyleState(
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    buttonType: BoxButtonState.Type = BoxButtonState.Type.Filled,
    buttonSize: BoxButtonState.Size = BoxButtonState.Size.ExtraLarge,
    roundingShape: CornerBasedShape = YdsRounding.large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): MutableState<BoxButtonState> = rememberSaveable(
    isDisabled, isWarned, buttonType, buttonSize, roundingShape, interactionSource,
    stateSaver = BoxButtonState.Saver
) {
    mutableStateOf(
        BoxButtonState(
            isDisabled, isWarned, buttonType, buttonSize, roundingShape, interactionSource
        )
    )
}

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
    styleState: BoxButtonState
) {
    val buttonColors = buttonColors(
        backgroundColor = styleState.backgroundColor,
        contentColor = styleState.contentColor,
        disabledBackgroundColor = styleState.disabledBackgroundColor,
        disabledContentColor = styleState.disabledContentColor
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .height(styleState.height),
        enabled = !styleState.isDisabled,
        elevation = elevation(0.dp, 0.dp, 0.dp),
        colors = buttonColors,
        border = if (styleState.buttonType == BoxButtonState.Type.Line) {
            BorderStroke(
                YdsBorder.normal,
                styleState.contentColor
            )
        } else { null },
        interactionSource = styleState.interactionSource,
        shape = styleState.roundingShape,
        contentPadding = PaddingValues(
            horizontal = styleState.horizontalPadding
        )
    ) {
        leftIcon?.let { leftIconId ->
                YdsIcon(
                    id = leftIconId,
                    iconSize = styleState.iconSize,
                    tint = styleState.contentColor
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

        Text(
            text = text,
            style = styleState.typo
        )

        rightIcon?.let { rightIconId ->
            Spacer(modifier = Modifier.width(4.dp))
            YdsIcon(
                id = rightIconId,
                iconSize = styleState.iconSize,
                tint = styleState.contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxButtonPreview() {
    var buttonState1 by rememberBoxButtonStyleState()
    val buttonState2 by rememberBoxButtonStyleState(
        isWarned = true,
        buttonType = BoxButtonState.Type.Line
    )

    YdsTheme {
        Column {
            BoxButton(
                onClick = { },
                text = "Filled",
                styleState = buttonState1,
                leftIcon = R.drawable.ic_ground_filled
            )
            BoxButton(
                onClick = {
                          buttonState1 = buttonState1.copy(
                              isDisabled = true,
                              buttonType = BoxButtonState.Type.Tinted
                          )
                },
                text = "Line",
                styleState = buttonState2
            )
        }
    }
}