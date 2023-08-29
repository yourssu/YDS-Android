package com.yourssu.design.system.compose.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.foundation.LocalContentColor

/**
 * todo: 설명 작성하기
 *
 * Clickable Surface
 */
@Composable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = YdsTheme.colors.bgNormal,
    contentColor: Color = LocalContentColor.current,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Box(
            modifier = modifier
                .surface(
                    shape = shape,
                    backgroundColor = color,
                    border = border,
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = onClick,
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
): Modifier = this
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)