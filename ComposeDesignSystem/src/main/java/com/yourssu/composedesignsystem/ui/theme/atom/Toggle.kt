package com.yourssu.composedesignsystem.ui.theme.atom

import android.os.Parcelable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToggleState(
    private val _isSelected: Boolean,
    private val _isDisabled: Boolean
) : Parcelable {
    @IgnoredOnParcel
    var isSelected by mutableStateOf(_isSelected)
    @IgnoredOnParcel
    var isDisabled by mutableStateOf(_isDisabled)

    val defaultTrackColor: Color
        @Composable get() = YdsTheme.colors.buttonBG
    val selectedTrackColor: Color
        @Composable get() = YdsTheme.colors.buttonPoint

    val thumbColor: Color
        @Composable get() = if (isDisabled) {
            YdsTheme.colors.buttonDisabled
        } else {
            YdsTheme.colors.buttonBright
        }

    val thumbBorderColor: Color
        @Composable get() = YdsTheme.colors.borderNormal
}

@Composable
fun rememberToggleState(
    isSelected: Boolean = false,
    isDisabled: Boolean = false
): ToggleState = rememberSaveable(isSelected, isDisabled) {
    ToggleState(isSelected, isDisabled)
}

@Composable
fun Toggle(
    state: ToggleState,
    onCheckedChange: (Boolean) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val transition = updateTransition(state.isSelected, label = "Selected indicator")

    val trackColor by transition.animateColor(label = "trackColor") { selected ->
        if (selected && !state.isDisabled)
            state.selectedTrackColor
        else
            state.defaultTrackColor
    }
    val offset by transition.animateDp(
        label = "offset",
        transitionSpec = {
            tween(
                durationMillis = 100,
                easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)  // TODO 애니메이션 따로 정리해두기
            )
        }
    ) { selected ->
        if (selected) 10.dp else (-10).dp
    }

    Box(
        modifier = Modifier
            .size(
                width = 51.dp,
                height = 31.dp
            )
            .clip(RoundedCornerShape(50))
            .background(color = trackColor)
            .toggleable(
                value = state.isSelected,
                interactionSource = interactionSource,
                indication = null,
                enabled = !state.isDisabled,
                role = Role.Switch,
                onValueChange = { newValue ->
                    state.isSelected = newValue
                    onCheckedChange(newValue)
                }
            )
            .offset(x = offset),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(27.dp)
                .clip(CircleShape)
                .background(color = state.thumbColor)
                .border(
                    width = YdsTheme.border.thin,
                    color = state.thumbBorderColor,
                    shape = CircleShape
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TogglePreview() {
    val state1 = rememberToggleState(isSelected = true)
    val state2 = rememberToggleState(isDisabled = true)
    YdsTheme {
        Column {
            Toggle(
                state = state1,
                onCheckedChange = {
                    state2.isSelected = it
                }
            )
            Toggle(state = state2, onCheckedChange = {})
        }
    }
}