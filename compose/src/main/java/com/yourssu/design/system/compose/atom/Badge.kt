package com.yourssu.design.system.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.foundation.IconSize
import com.yourssu.design.system.compose.foundation.ItemColor
import com.yourssu.design.system.compose.foundation.YdsIcon

@Composable
fun Badge(
    text: String,
    itemColor: ItemColor,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null
) {
    Surface(
        modifier = modifier
            .requiredHeight(24.dp)
            .wrapContentWidth(),
        shape = YdsTheme.rounding.small,
        color = itemColor.getSemanticColor(),
        contentColor = YdsTheme.colors.monoItemText
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = if (icon == null) 12.dp else 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let { iconId ->
                YdsIcon(
                    id = iconId,
                    iconSize = IconSize.ExtraSmall
                )
                Spacer(Modifier.width(4.dp))
            }
            Text(text = text, style = YdsTheme.typography.caption1)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BadgePreview() {
    var text by remember { mutableStateOf("에메랄드 뱃지") }
    var itemColor by remember { mutableStateOf(ItemColor.Emerald) }

    YdsTheme {
        Column {
            Badge(
                text = text, itemColor = itemColor, icon = R.drawable.ic_ground_filled
            )
            Badge(text = "핑크 뱃지", itemColor = ItemColor.Pink)
            BoxButton(
                onClick = {
                    text = "퍼플 뱃지"
                    itemColor = ItemColor.Purple
                },
                text = "Click"
            )
        }
    }
}