package com.yourssu.design.system.compose.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.rule.YdsBorder
import com.yourssu.design.system.compose.states.ButtonColorState

@Composable
fun NoRippleButton(
    onClick: () -> Unit,
    colors: ButtonColorState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showBorder: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val localPressed by interactionSource.collectIsPressedAsState()
    val buttonColors = colors.apply { pressed = localPressed }
    val contentColor by buttonColors.contentColor(enabled)

    Surface(
        modifier = modifier
            .noRippleClickable(interactionSource, onClick = onClick),
        shape = shape,
        color = buttonColors.backgroundColor(enabled).value,
        contentColor = contentColor,
        border = if (showBorder) BorderStroke(YdsBorder.Normal.dp, contentColor) else null,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(value = MaterialTheme.typography.button) {
                Row(
                    modifier = Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}