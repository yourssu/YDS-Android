package com.yourssu.design.system.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.Surface
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.rule.ItemColor

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
        rounding = 2.dp,
        color = itemColor.semanticColor,
        contentColor = YdsTheme.colors.monoItemText
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = if (icon == null) 12.dp else 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let { iconId ->
                Icon(
                    id = iconId,
                    iconSize = IconSize.ExtraSmall
                )
                Spacer(Modifier.width(4.dp))
            }
            YdsText(text = text, style = YdsTheme.typography.caption1)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BadgePreview() {
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