package com.yourssu.composedesignsystem.ui.theme.atom

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.base.NoRippleButton
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import com.yourssu.composedesignsystem.ui.theme.states.ButtonColorState
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlainButtonState(
    private val _text: String = "",
    @DrawableRes private val _leftIcon: Int? = null,
    @DrawableRes private val _rightIcon: Int? = null,
    private val _isDisabled: Boolean = false,
    private val _isWarned: Boolean = false,
    private val _isPointed: Boolean = false,
    private val _buttonSize: Size = Size.Medium
) : Parcelable {
    /**
     * [BoxButtonState]의 설명 참고
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
    var isPointed by mutableStateOf(_isPointed)
    @IgnoredOnParcel
    var buttonSize by mutableStateOf(_buttonSize)


    val colorState: ButtonColorState
        @Composable get() = ButtonColorState(
            contentColor = when {
                isWarned -> YdsTheme.colors.buttonWarned
                isPointed -> YdsTheme.colors.buttonPoint
                else -> YdsTheme.colors.buttonNormal
            },
            disabledContentColor = YdsTheme.colors.buttonDisabled
        )

    private val sizeState: ButtonSizeState
        @Composable get() = plainButtonSizeStateBySize(size = buttonSize)
    val typo: TextStyle
        @Composable get() = sizeState.typo
    val iconSize: IconSize
        @Composable get() = sizeState.iconSize

    enum class Size {
        Large, Medium, Small
    }

    companion object {
        private const val textKey = "text"
        private const val leftIconKey = "leftIcon"
        private const val rightIconKey = "rightIcon"
        private const val disabledKey = "disabled"
        private const val warnedKey = "warned"
        private const val pointedKey = "pointed"
        private const val buttonSizeKey = "buttonSize"

        val Saver = run {
            mapSaver(
                save = {
                    mapOf(
                        textKey to it.text,
                        leftIconKey to it.leftIcon,
                        rightIconKey to it.rightIcon,
                        disabledKey to it.isDisabled,
                        warnedKey to it.isWarned,
                        pointedKey to it.isPointed,
                        buttonSizeKey to it.buttonSize
                    )
                },
                restore = {
                    PlainButtonState(
                        it[textKey] as String,
                        it[leftIconKey] as Int?,
                        it[rightIconKey] as Int?,
                        it[disabledKey] as Boolean,
                        it[warnedKey] as Boolean,
                        it[pointedKey] as Boolean,
                        it[buttonSizeKey] as Size
                    )
                }
            )
        }
    }
}

@Composable
fun rememberPlainButtonState(
    text: String = "",
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    isPointed: Boolean = false,
    buttonSize: PlainButtonState.Size = PlainButtonState.Size.Medium
): PlainButtonState = rememberSaveable(
    text, leftIcon, rightIcon, isDisabled, isWarned, isPointed, buttonSize
) {
    PlainButtonState(text, leftIcon, rightIcon, isDisabled, isWarned, isPointed, buttonSize)
}

@Composable
private fun plainButtonSizeStateBySize(
    size: PlainButtonState.Size
): ButtonSizeState = when (size) {
    PlainButtonState.Size.Large -> ButtonSizeState(iconSize = IconSize.Medium)
    PlainButtonState.Size.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button3,
        iconSize = IconSize.Small
    )
    PlainButtonState.Size.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
        iconSize = IconSize.ExtraSmall
    )
}

@Composable
fun PlainButton(
    onClick: () -> Unit,
    state: PlainButtonState,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    NoRippleButton(
        onClick = onClick,
        modifier = modifier,
        enabled = !state.isDisabled,
        colors = state.colorState,
        elevation = elevation(0.dp, 0.dp, 0.dp),
        interactionSource = interactionSource,
        contentPadding = PaddingValues(0.dp)
    ) {
        if (state.buttonSize == PlainButtonState.Size.Large) {
            // leftIcon이 null이라면 rightIcon 할당
            val iconResource = state.leftIcon ?: state.rightIcon

            iconResource?.let { iconRes ->
                YdsIcon(
                    id = iconRes,
                    iconSize = state.iconSize
                )
            }
        } else {
            state.leftIcon?.let { leftIconId ->
                YdsIcon(
                    id = leftIconId,
                    iconSize = state.iconSize
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            Text(
                text = state.text,
                style = state.typo
            )

            if (state.leftIcon == null) {
                state.rightIcon?.let { rightIconId ->
                    Spacer(modifier = Modifier.width(2.dp))
                    YdsIcon(
                        id = rightIconId,
                        iconSize = state.iconSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlainButtonPreview() {
    val buttonState1 = rememberPlainButtonState(
        text = "MEDIUM",
        leftIcon = R.drawable.ic_ground_filled,
        buttonSize = PlainButtonState.Size.Medium
    )
    val buttonState2 = rememberPlainButtonState(
        text = "click me!",
        leftIcon = R.drawable.ic_ground_filled,
        buttonSize = PlainButtonState.Size.Small,
        isWarned = true
    )

    YdsTheme {
        Column {
            PlainButton(
                onClick = {},
                state = buttonState1
            )
            PlainButton(
                onClick = {
                    buttonState1.text = "Small"
                    buttonState1.isPointed = true
                    buttonState1.buttonSize = PlainButtonState.Size.Small
                },
                state = buttonState2
            )
        }
    }
}