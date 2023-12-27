package com.yourssu.design.system.compose.atom

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.ydsClickable
import com.yourssu.design.system.compose.rule.YdsInAndOutEasing
import com.yourssu.design.system.compose.rule.YdsBorder

@Composable
fun Toggle(
    modifier: Modifier = Modifier,
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

    val clickableModifier = if (isDisabled) modifier else
        modifier.ydsClickable(interactionSource) { onCheckedChange(!checked) }

    Box(
        modifier = modifier
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
                    width = YdsBorder.Thin.dp,
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