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
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import com.yourssu.composedesignsystem.ui.theme.states.ButtonColorState
import com.yourssu.composedesignsystem.ui.theme.util.alterColorIfPressed

// interactionSource가 껴있으면 클릭 할 때마다 객체가 재생성되는 건가?
// 만약 그렇다면 interactionSource를 외부로 옮길 필요가 있을 듯
data class BoxButtonState(
    private val text: String = "",
    @DrawableRes private val leftIcon: Int? = null,
    @DrawableRes private val rightIcon: Int? = null,
    private val isDisabled: Boolean = false,
    private val isWarned: Boolean = false,
    private val buttonType: Type = Type.Filled,
    private val buttonSize: Size = Size.Large,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    /**
     * 외부에서 BoxButtonState의 속성을 변경할 때 접근하는 프로퍼티입니다.
     *
     * 생성자의 프로퍼티는 외부에서 접근하면 안되므로
     * private 접근 지정자가 지정되어 있습니다.
     *
     * 변경된 속성과 관련된 compose 함수를 자동을
     * recomposition 시킬 수 있도록 MutableState 객체로 정의되어 있습니다.
     */
    var textState by mutableStateOf(text)
    var leftIconState by mutableStateOf(leftIcon)
    var rightIconState by mutableStateOf(rightIcon)
    var isDisabledState by mutableStateOf(isDisabled)
    var isWarnedState by mutableStateOf(isWarned)
    var buttonTypeState by mutableStateOf(buttonType)
    var buttonSizeState by mutableStateOf(buttonSize)

    private val isPressed: Boolean
        @Composable get() = interactionSource.collectIsPressedAsState().value

    private val colorState: ButtonColorState
        @Composable get() = boxButtonColorByType(type = buttonTypeState)

    private val sizeState: ButtonSizeState
        @Composable get() = boxButtonSizeStateBySize(size = buttonSizeState)

    val contentColor: Color
        @Composable get() = when {
            isDisabledState -> colorState.disabledContentColor
            isWarnedState -> colorState.warnedContentColor
            else -> colorState.contentColor
        }.alterColorIfPressed(isPressed = isPressed)

    val backgroundColor: Color
        @Composable get() = when {
            isDisabledState -> colorState.disabledBgColor
            isWarnedState -> colorState.warnedBgColor
            else -> colorState.bgColor
        }.alterColorIfPressed(isPressed = isPressed)

    val disabledContentColor: Color
        @Composable get() = colorState.disabledContentColor
    val disabledBackgroundColor: Color
        @Composable get() = colorState.disabledBgColor

    val typo: TextStyle
        @Composable get() = sizeState.typo
    val iconSize: IconSize
        @Composable get() = sizeState.iconSize
    val height: Dp
        @Composable get() = sizeState.height
    val horizontalPadding: Dp
        @Composable get() = sizeState.horizontalPadding

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

            mapSaver(
                save = {
                    mapOf(
                        textKey to it.textState,
                        leftIconKey to it.leftIconState,
                        rightIconKey to it.rightIconState,
                        disabledKey to it.isDisabledState,
                        warnedKey to it.isWarnedState,
                        buttonTypeKey to it.buttonTypeState,
                        buttonSizeKey to it.buttonSizeState
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
                        it[buttonSizeKey] as Size
                    )
                }
            )
        }
    }
}

@Composable
fun rememberBoxButtonState(
    text: String,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    buttonType: BoxButtonState.Type = BoxButtonState.Type.Filled,
    buttonSize: BoxButtonState.Size = BoxButtonState.Size.Large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): BoxButtonState = rememberSaveable(
    text, leftIcon, rightIcon, isDisabled, isWarned, buttonType, buttonSize, interactionSource,
    saver = BoxButtonState.Saver
) {
    BoxButtonState(text, leftIcon, rightIcon, isDisabled, isWarned, buttonType, buttonSize, interactionSource)
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
    state: BoxButtonState,
    modifier: Modifier = Modifier,
    rounding: CornerBasedShape = YdsTheme.rounding.large
) {
    val buttonColors = buttonColors(
        backgroundColor = state.backgroundColor,
        contentColor = state.contentColor,
        disabledBackgroundColor = state.disabledBackgroundColor,
        disabledContentColor = state.disabledContentColor
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .height(state.height),
        enabled = !state.isDisabledState,
        elevation = elevation(0.dp, 0.dp, 0.dp),
        colors = buttonColors,
        border = if (state.buttonTypeState == BoxButtonState.Type.Line) {
            BorderStroke(
                YdsBorder.normal,
                state.contentColor
            )
        } else { null },
        interactionSource = state.interactionSource,
        shape = rounding,
        contentPadding = PaddingValues(
            horizontal = state.horizontalPadding
        )
    ) {
        state.leftIconState?.let { leftIconId ->
                YdsIcon(
                    id = leftIconId,
                    iconSize = state.iconSize,
                    tint = state.contentColor
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

        Text(
            text = state.textState,
            style = state.typo
        )

        state.rightIconState?.let { rightIconId ->
            Spacer(modifier = Modifier.width(4.dp))
            YdsIcon(
                id = rightIconId,
                iconSize = state.iconSize,
                tint = state.contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxButtonPreview() {
    val buttonState1 = rememberBoxButtonState(
        text = "Filled",
        leftIcon = R.drawable.ic_ground_filled
    )
    val buttonState2 = rememberBoxButtonState(
        text = "Line",
        isWarned = true,
        buttonType = BoxButtonState.Type.Line
    )

    YdsTheme {
        Column {
            BoxButton(
                onClick = { },
                state = buttonState1
            )
            BoxButton(
                onClick = {
//                    buttonState1 = buttonState1.copy(
//                      isDisabled = true,
//                      buttonType = BoxButtonState.Type.Tinted
//                    )
                    buttonState1.isDisabledState = true
                    buttonState1.buttonTypeState = BoxButtonState.Type.Tinted

                },
                state = buttonState2
            )
        }
    }
}