package com.yourssu.composedesignsystem.ui.theme.atom

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsTypo
import com.yourssu.composedesignsystem.ui.theme.rule.YdsRounding
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import com.yourssu.composedesignsystem.ui.theme.states.ButtonColorState
import com.yourssu.composedesignsystem.ui.theme.util.maybePressed

data class BoxButtonState(
    val text: String = "",
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null,
    val isDisabled: Boolean = false,
    val isWarned: Boolean = false,
    val buttonType: Type = Type.Filled,
    val buttonSize: Size = Size.ExtraLarge,
    val rounding: CornerBasedShape = YdsRounding.large,
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
            val textKey = "text"
            val leftIconKey = "leftIcon"
            val rightIconKey = "rightIcon"
            val disabledKey = "disabled"
            val warnedKey = "warned"
            val buttonTypeKey = "buttonType"
            val buttonSizeKey = "buttonSize"
            val roundingKey = "rounding"

            mapSaver(
                save = {
                    mapOf(
                        textKey to it.text,
                        leftIconKey to it.leftIcon,
                        rightIconKey to it.rightIcon,
                        disabledKey to it.isDisabled,
                        warnedKey to it.isWarned,
                        buttonTypeKey to it.buttonType,
                        buttonSizeKey to it.buttonSize,
                        roundingKey to it.rounding
                    )
                },
                restore = {
                    BoxButtonState(
                        it[textKey] as String,
                        it[leftIconKey] as Int?,
                        it[rightIconKey] as Int?,
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
    buttonState: BoxButtonState,
    modifier: Modifier = Modifier
) {
    
}

@Composable
fun BoxButton(
    onClick: () -> Unit,
    text: String
) {
}

@Preview
@Composable
fun BoxButtonPreview() {

}

@Composable
fun ButtonTest(
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    val isPressed = interactionSource.collectIsPressedAsState().value
    Log.d("buttonTest", "ButtonTest: $isPressed")
    Column {
        Button(onClick = { /*TODO*/ }) {

        }
        Text(text = "$isPressed")
    }
}