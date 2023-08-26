package com.yourssu.design.system.compose.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class IconSize(val value: Dp) {
    object ExtraSmall : IconSize(16.dp)
    object Small : IconSize(20.dp)
    object Medium : IconSize(24.dp)
    object Large : IconSize(48.dp)
}

@Composable
fun YdsIcon(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    iconSize: IconSize = IconSize.Medium,
    tint: Color = LocalContentColor.current
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = id),
        contentDescription = "$id",
        modifier = Modifier
            .size(iconSize.value)
            .then(modifier),
        tint = tint
    )
}