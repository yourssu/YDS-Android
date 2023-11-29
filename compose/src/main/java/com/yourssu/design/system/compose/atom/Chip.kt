package com.yourssu.design.system.compose.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.yourssu.design.system.compose.base.YdsText

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    isDisabled: Boolean = false,
    onSelectedChange: (Boolean) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val contentColor = if (isSelected) {
        YdsTheme.colors.textBright
    } else {
        YdsTheme.colors.textTertiary
    }

    val backgroundColor = if (isSelected) {
        YdsTheme.colors.buttonPoint
    } else {
        YdsTheme.colors.buttonDisabledBG
    }

    Box(
        modifier = modifier
            .height(24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isDisabled.not(),
                onClick = { onSelectedChange(!isSelected) }
            ),
        contentAlignment = Alignment.Center,
    ) {
        YdsText(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = contentColor,
            style = YdsTheme.typography.caption1
        )
    }
}


@Preview(name = "Chip")
@Composable
private fun PreviewChip() {
    var checked1 by remember { mutableStateOf(true) }
    var checked2 by remember { mutableStateOf(false) }
    val onSelectedChange: (Boolean) -> Unit = { it -> checked1 = it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YdsTheme.colors.bgNormal),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Chip(
            text = "IT대학",
            isSelected = checked1,
            isDisabled = false,
            onSelectedChange = onSelectedChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        Chip(
            text = "IT대학",
            isSelected = checked2,
            isDisabled = true,
            onSelectedChange = onSelectedChange
        )
    }
}