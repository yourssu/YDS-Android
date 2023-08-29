package com.yourssu.design.system.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.rule.YdsBorder

class SquircleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawSquirclePath(size = size)
        )
    }

    private fun drawSquirclePath(size: Size): Path =
        Path().apply {
            val radius = size.width / 2
            val diameter = size.width
            val length = radius * 0.2f
            reset()

            lineTo(x = 0.0f, y = radius)
            cubicTo(
                x1 = 0.0f, y1 = length,
                x2 = length, y2 = 0.0f,
                x3 = radius, y3 = 0.0f,
            )

            lineTo(x = radius, y = 0.0f)
            cubicTo(
                x1 = diameter - length, y1 = 0.0f,
                x2 = diameter, y2 = length,
                x3 = diameter, y3 = radius,
            )

            lineTo(x = diameter, y = radius)
            cubicTo(
                x1 = diameter, y1 = diameter - length,
                x2 = diameter - length, y2 = diameter,
                x3 = radius, y3 = diameter,
            )

            lineTo(x = radius, y = diameter)
            cubicTo(
                x1 = length, y1 = diameter,
                x2 = 0.0f, y2 = diameter - length,
                x3 = 0.0f, y3 = radius,
            )

            close()
        }
}


enum class ProfileImageViewSize(val value: Dp) {
    ExtraSmall(24.dp),
    Small(32.dp),
    Medium(48.dp),
    Large(72.dp),
    ExtraLarge(96.dp)
}

@Composable
fun ProfileImageView(
    @DrawableRes imageResource: Int,
    size: ProfileImageViewSize = ProfileImageViewSize.Medium
) {
    ProfileImageView(
        painter = painterResource(id = imageResource),
        size = size
    )
}

@Composable
fun ProfileImageView(
    painter: Painter,
    size: ProfileImageViewSize = ProfileImageViewSize.Medium
) {
    Image(
        painter = painter,
        contentDescription = "Profile Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.value)
            .clip(SquircleShape())
            .border(
                width = YdsBorder.Normal.dp,
                color = YdsTheme.colors.borderNormal,
                shape = SquircleShape()
            )
    )
}

@Preview(showSystemUi = true)
@Composable
fun ProfileImageViewPreview() {
    val sizeList = listOf(
        ProfileImageViewSize.ExtraSmall,
        ProfileImageViewSize.Small,
        ProfileImageViewSize.Medium,
        ProfileImageViewSize.Large,
        ProfileImageViewSize.ExtraLarge
    )
    YdsTheme {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            sizeList.forEach {
                ProfileImageView(
                    imageResource = R.drawable.ic_adbadge_filled,
                    size = it
                )
            }
        }
    }
}