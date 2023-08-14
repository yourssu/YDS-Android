package com.yourssu.design.system.compose.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme

@Composable
fun ListToggleItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = YdsTheme.colors.bgNormal),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = YdsTheme.typography.body1
        )

        Spacer(Modifier.width(8.dp))

        Toggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
            isDisabled = isDisabled
        )

        Spacer(Modifier.width(20.dp))
    }
}

@Preview
@Composable
fun ListToggleItemPreview() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    YdsTheme {
        Column {
            ListToggleItem(text = "로그아웃", checked = checked1, onCheckedChange = { checked1 = it })
            ListToggleItem(text = "테스트", checked = checked2, onCheckedChange = { checked2 = it })
        }
    }
}