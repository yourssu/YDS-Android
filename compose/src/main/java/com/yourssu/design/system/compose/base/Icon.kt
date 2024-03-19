package com.yourssu.design.system.compose.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.foundation.LocalYdsContentColor

sealed class IconSize(val value: Dp) {
    object ExtraSmall : IconSize(16.dp)
    object Small : IconSize(20.dp)
    object Medium : IconSize(24.dp)
    object Large : IconSize(48.dp)
}

@Composable
fun Icon(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconSize: IconSize = IconSize.Medium,
    tint: Color = LocalYdsContentColor.current,
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = id),
        modifier = modifier,
        contentDescription = contentDescription,
        iconSize = iconSize,
        tint = tint,
    )
}

@Composable
fun Icon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconSize: IconSize = IconSize.Medium,
    tint: Color = LocalYdsContentColor.current,
) {
    Icon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        iconSize = iconSize,
        tint = tint,
    )
}

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    iconSize: IconSize = IconSize.Medium,
    tint: Color = LocalYdsContentColor.current,
) {
    val colorFilter = ColorFilter.tint(tint)
    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }
    Box(
        modifier
            .toolingGraphicsLayer()
            .size(iconSize.value)
            .paint(
                painter = painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit,
            ).then(semantics),
    )
}
