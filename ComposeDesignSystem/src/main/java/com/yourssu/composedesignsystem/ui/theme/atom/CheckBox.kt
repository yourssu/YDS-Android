package com.yourssu.composedesignsystem.ui.theme.atom

import android.os.Parcelable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import com.yourssu.composedesignsystem.ui.theme.states.ButtonSizeState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckBoxState(
    private val _text: String,
    private val _size: Size,
    private val _isSelected: Boolean = false,
    private val _isDisabled: Boolean = false
) : Parcelable {

    @IgnoredOnParcel
    var text by mutableStateOf(_text)
    @IgnoredOnParcel
    var size by mutableStateOf(_size)
    @IgnoredOnParcel
    var isSelected by mutableStateOf(_isSelected)
    @IgnoredOnParcel
    var isDisabled by mutableStateOf(_isDisabled)

    val contentColor: Color
        @Composable get() = when {
            isDisabled -> YdsTheme.colors.buttonDisabled
            isSelected -> YdsTheme.colors.buttonPoint
            else -> YdsTheme.colors.buttonNormal
        }

    val sizeState: ButtonSizeState
        @Composable get() = checkBoxSizeStateBySize(size)

    enum class Size {
        Small, Medium, Large
    }
}

@Composable
fun rememberCheckBoxState(
    text: String,
    size: CheckBoxState.Size = CheckBoxState.Size.Medium,
    isSelected: Boolean = false,
    isDisabled: Boolean = false
): CheckBoxState = rememberSaveable(text, size, isSelected, isDisabled) {
    CheckBoxState(text, size, isSelected, isDisabled)
}

@Composable
private fun checkBoxSizeStateBySize(
    size: CheckBoxState.Size
): ButtonSizeState = when (size) {
    CheckBoxState.Size.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
        iconSize = IconSize.ExtraSmall,
        betweenSpace = 4.dp
    )
    CheckBoxState.Size.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button3,
        iconSize = IconSize.Small,
        betweenSpace = 8.dp
    )
    CheckBoxState.Size.Large -> ButtonSizeState(
        typo = YdsTheme.typography.button3,
        iconSize = IconSize.Small,
        betweenSpace = 8.dp
    )
}


@Composable
fun CheckBox(
    state: CheckBoxState,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .wrapContentWidth()
            .toggleable(
                value = state.isSelected,
                enabled = !state.isDisabled,
                interactionSource = interactionSource,
                indication = null,
                onValueChange = { newValue ->
                    state.isSelected = newValue
                    onCheckedChange(newValue)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        YdsIcon(
            id = if (state.isSelected) {
                R.drawable.ic_checkcircle_filled
            } else {
                R.drawable.ic_checkcircle_line
            },
            iconSize = state.sizeState.iconSize,
            tint = state.contentColor
        )

        Spacer(Modifier.width(state.sizeState.betweenSpace))

        Text(
            text = state.text,
            style = state.sizeState.typo,
            color = state.contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckBoxPreview() {
    val state1 = rememberCheckBoxState(
        text = "텍스트",
        isSelected = true
    )
    val state2 = rememberCheckBoxState(
        text = "Disabled",
        size = CheckBoxState.Size.Large,
        isDisabled = true,
        isSelected = true
    )

    YdsTheme {
        Column {
            CheckBox(state = state1, onCheckedChange = {
                state2.isSelected = it
            })
            CheckBox(state = state2, onCheckedChange = {})
        }
    }
}