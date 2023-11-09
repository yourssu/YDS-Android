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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.rule.YdsBorder
import com.yourssu.design.system.compose.states.ButtonColorState

/**
 * (전) NoRippleButton
 *
 * BoxButton, PlainButton의 베이스가 되는, Ripple 효과가 없는 Composable 함수 입니다.
 *
 * @see com.yourssu.design.system.compose.atom.BoxButton
 * @see com.yourssu.design.system.compose.atom.PlainButton
 */
@Composable
internal fun YdsBaseButton(
    onClick: () -> Unit,
    colors: ButtonColorState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showBorder: Boolean = false,
    rounding: Dp = 8.dp,
    contentPadding: PaddingValues = YdsButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    minWidth: Dp = YdsButtonDefaults.MinWidth,
    minHeight: Dp = YdsButtonDefaults.MinHeight,
    content: @Composable RowScope.() -> Unit,
) {
    val localPressed by interactionSource.collectIsPressedAsState()
    val buttonColors = colors.apply { pressed = localPressed }
    val contentColor by buttonColors.contentColor(enabled)

    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        rounding = rounding,
        color = buttonColors.backgroundColor(enabled).value,
        contentColor = contentColor,
        border = if (showBorder) BorderStroke(YdsBorder.Normal.dp, contentColor) else null,
        interactionSource = interactionSource,
    ) {
        ProvideTextStyle(value = YdsTheme.typography.button2) {
            Row(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = minWidth,
                        minHeight = minHeight,
                    )
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

object YdsButtonDefaults {
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 12.dp

    val ContentPadding = PaddingValues(
        horizontal = ButtonHorizontalPadding,
        vertical = ButtonVerticalPadding,
    )

    // YDS에 명시되어 있지 않아서 Material에서 가져옴
    val MinWidth = 64.dp
    val MinHeight = 36.dp
}
