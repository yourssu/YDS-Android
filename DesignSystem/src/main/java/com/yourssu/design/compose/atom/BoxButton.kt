package com.yourssu.design.compose.atom

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.R
import com.yourssu.design.compose.YdsTheme
import com.yourssu.design.compose.base.NoRippleButton
import com.yourssu.design.compose.foundation.IconSize
import com.yourssu.design.compose.foundation.YdsIcon
import com.yourssu.design.compose.states.ButtonSizeState
import com.yourssu.design.compose.states.ButtonColorState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoxButtonState(
    private val _text: String = "",
    @DrawableRes private val _leftIcon: Int? = null,
    @DrawableRes private val _rightIcon: Int? = null,
    private val _isDisabled: Boolean = false,
    private val _isWarned: Boolean = false,
    private val _buttonType: Type = Type.Filled,
    private val _buttonSize: Size = Size.Large
) : Parcelable {
    /**
     * 외부에서 BoxButtonState의 속성을 변경할 때 접근하는 프로퍼티입니다.
     *
     * 생성자의 프로퍼티는 외부에서 접근하면 안되므로
     * private 접근 지정자가 지정되어 있습니다.
     *
     * 변경된 속성과 관련된 compose 함수를 자동을
     * recomposition 시킬 수 있도록 MutableState 객체로 정의되어 있습니다.
     */
    @IgnoredOnParcel
    var text by mutableStateOf(_text)
    @IgnoredOnParcel
    var leftIcon by mutableStateOf(_leftIcon)
    @IgnoredOnParcel
    var rightIcon by mutableStateOf(_rightIcon)
    @IgnoredOnParcel
    var isDisabled by mutableStateOf(_isDisabled)
    @IgnoredOnParcel
    var isWarned by mutableStateOf(_isWarned)
    @IgnoredOnParcel
    var buttonType by mutableStateOf(_buttonType)
    @IgnoredOnParcel
    var buttonSize by mutableStateOf(_buttonSize)

    val colorState: ButtonColorState
        @Composable get() = boxButtonColorByType(
            isWarned = isWarned,
            type = buttonType
        )

    private val sizeState: ButtonSizeState
        @Composable get() = boxButtonSizeStateBySize(size = buttonSize)
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
}

@Composable
fun rememberBoxButtonState(
    text: String,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    buttonType: BoxButtonState.Type = BoxButtonState.Type.Filled,
    buttonSize: BoxButtonState.Size = BoxButtonState.Size.Large
): BoxButtonState = rememberSaveable(
    text, leftIcon, rightIcon, isDisabled, isWarned, buttonType, buttonSize
) {
    BoxButtonState(text, leftIcon, rightIcon, isDisabled, isWarned, buttonType, buttonSize)
}

@Composable
private fun boxButtonColorByType(
    isWarned: Boolean,
    type: BoxButtonState.Type
): ButtonColorState = when (type) {
    BoxButtonState.Type.Filled -> ButtonColorState(
        contentColor = YdsTheme.colors.buttonBright,
        disabledContentColor = YdsTheme.colors.buttonDisabled,
        bgColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> YdsTheme.colors.buttonPoint
        },
        disabledBgColor = YdsTheme.colors.buttonDisabledBG,
    )
    BoxButtonState.Type.Tinted -> ButtonColorState(
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
    BoxButtonState.Type.Line -> ButtonColorState(
        contentColor = when {
            isWarned -> YdsTheme.colors.buttonWarned
            else -> YdsTheme.colors.buttonPoint
        },
        disabledContentColor = YdsTheme.colors.buttonDisabled
    )
}

@Composable
private fun boxButtonSizeStateBySize(
    size: BoxButtonState.Size
): ButtonSizeState = when (size) {
    BoxButtonState.Size.ExtraLarge -> ButtonSizeState(
        typo = YdsTheme.typography.button1,
        iconSize = IconSize.Medium,
        height = 56.dp,
        horizontalPadding = 16.dp
    )
    BoxButtonState.Size.Large -> ButtonSizeState(
        typo = YdsTheme.typography.button2,
        iconSize = IconSize.Medium,
        height = 48.dp,
        horizontalPadding = 16.dp
    )
    BoxButtonState.Size.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button2,
        iconSize = IconSize.Medium,
        height = 40.dp,
        horizontalPadding = 12.dp
    )
    BoxButtonState.Size.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
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
    rounding: CornerBasedShape = YdsTheme.rounding.large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    NoRippleButton(
        onClick = onClick,
        colors = state.colorState,
        modifier = Modifier
            .then(modifier)
            .height(state.height),
        enabled = !state.isDisabled,
        showBorder = (state.buttonType == BoxButtonState.Type.Line),
        elevation = elevation(0.dp, 0.dp, 0.dp),
        interactionSource = interactionSource,
        shape = rounding,
        contentPadding = PaddingValues(
            horizontal = state.horizontalPadding
        )
    ) {
        state.leftIcon?.let { leftIconId ->
                YdsIcon(
                    id = leftIconId,
                    iconSize = state.iconSize
                // tint 자동으로 contentColor로 지정됨
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

        Text(
            text = state.text,
            style = state.typo
        )

        state.rightIcon?.let { rightIconId ->
            Spacer(modifier = Modifier.width(4.dp))
            YdsIcon(
                id = rightIconId,
                iconSize = state.iconSize
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
                    buttonState1.isWarned = true
                    buttonState1.buttonType = BoxButtonState.Type.Line

                },
                state = buttonState2
            )
        }
    }
}