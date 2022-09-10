package com.yourssu.composedesignsystem.ui.theme.atom

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.IconSize
import com.yourssu.composedesignsystem.ui.theme.foundation.ItemColor
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class BadgeState(
    private val _text: String,
    private val _itemColor: ItemColor,
    @DrawableRes private val _icon: Int? = null
) : Parcelable {
    @IgnoredOnParcel
    var text by mutableStateOf(_text)
    @IgnoredOnParcel
    var itemColor by mutableStateOf(_itemColor)
    @IgnoredOnParcel
    var icon by mutableStateOf(_icon)
}

@Composable
fun rememberBadgeState(
    text: String,
    itemColor: ItemColor,
    @DrawableRes icon: Int? = null,
): BadgeState = rememberSaveable(text, itemColor, icon) {
    BadgeState(text, itemColor, icon)
}

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    state: BadgeState
) {
    Badge(
        modifier = modifier,
        text = state.text,
        itemColor = state.itemColor,
        icon = state.icon
    )
}

@Composable
fun Badge(
    text: String,
    itemColor: ItemColor,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null
) {
    Surface(
        modifier = Modifier
            .then(modifier)
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
    YdsTheme {
        Column {
            val state1 = rememberBadgeState(text = "에메랄드 뱃지", itemColor = ItemColor.Emerald, icon = R.drawable.ic_ground_filled)
            Badge(
                state = state1
            )
            Badge(text = "핑크 뱃지", itemColor = ItemColor.Pink)
            BoxButton(
                onClick = {
                    state1.text = "퍼플 뱃지"
                    state1.itemColor = ItemColor.Purple
                },
                state = rememberBoxButtonState(text = "Click")
            )
        }
    }
}