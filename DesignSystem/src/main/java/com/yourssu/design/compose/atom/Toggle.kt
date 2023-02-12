package com.yourssu.design.compose.atom

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.compose.YdsTheme
import com.yourssu.design.compose.base.noRippleClickable
import com.yourssu.design.compose.foundation.YdsInAndOutEasing

@Composable
fun Toggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isDisabled: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val transition = updateTransition(checked, label = "Selected indicator")

    val trackColor by transition.animateColor(label = "trackColor") { selected ->
        if (selected && !isDisabled)
            YdsTheme.colors.buttonPoint
        else
            YdsTheme.colors.buttonBG
    }
    val offset by transition.animateDp(
        label = "offset",
        transitionSpec = {
            tween(
                durationMillis = 100,
                easing = YdsInAndOutEasing
            )
        }
    ) { selected ->
        if (selected) 10.dp else (-10).dp
    }

    val clickableModifier = if (isDisabled) Modifier else
        Modifier.noRippleClickable(interactionSource) { onCheckedChange(!checked) }

    Box(
        modifier = Modifier
            .size(width = 51.dp, height = 31.dp)
            .clip(RoundedCornerShape(50))
            .background(color = trackColor)
            .then(clickableModifier)
            .offset(x = offset),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(27.dp)
                .clip(CircleShape)
                .background(
                    color = if (isDisabled) YdsTheme.colors.buttonDisabled
                    else YdsTheme.colors.buttonBright
                )
                .border(
                    width = YdsTheme.border.thin,
                    color = YdsTheme.colors.borderNormal,
                    shape = CircleShape
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TogglePreview() {
    var checked1 by remember { mutableStateOf(false) }
    var disabled by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    YdsTheme {
        Column {
            Toggle(checked = checked1, onCheckedChange = { checked1 = it }, isDisabled = disabled)
            Toggle(checked = checked2, onCheckedChange = { checked2 = it; disabled = it })
        }
    }
}