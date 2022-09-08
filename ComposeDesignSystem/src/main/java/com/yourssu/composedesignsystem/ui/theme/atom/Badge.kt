package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.ItemColor
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String,
    itemColor: ItemColor = ItemColor.Mono,
    @DrawableRes icon: Int? = null
) {
    val backgroundColor by itemColor.getSemanticColor()

    Surface(
        modifier = Modifier
            .then(modifier)
            .requiredHeight(24.dp)
            .wrapContentWidth(),
        shape = YdsTheme.rounding.small,
        color = backgroundColor,
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
            Text(text = text, style = YdsTheme.typography.button4)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BadgePreview() {
    YdsTheme {
        Column {
            Badge(
                text = "에메랄드 뱃지",
                itemColor = ItemColor.Emerald,
                icon = R.drawable.ic_ground_filled
            )
            Badge(text = "핑크 뱃지", itemColor = ItemColor.Pink)
        }
    }
}