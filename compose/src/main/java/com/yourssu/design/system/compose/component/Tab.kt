package com.yourssu.design.system.compose.component

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.foundation.LocalContentColor

/**
 *
 * Tabs organize content across different screens, data sets, and other interactions.
 *
 * A Tab represents a single page of content using a text label. It represents its
 * selected state by tinting the text label with [selectedContentColor], [unselectedContentColor].
 *
 * This should typically be used inside of a [FixedTabBar], [ScrollableTabBar]
 *
 * This Tab has slots for [YdsText]
 *
 * * Text can be a minimum of 1 character and a maximum of 6 characters without spaces.
 *
 * @param selected whether this tab is selected or not
 * @param onClick the callback to be invoked when this tab is selected
 * @param modifier optional [Modifier] for this tab
 * @param text the text label displayed in this tab
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this Tab. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this Tab in different [Interaction]s.
 * @param selectedContentColor the color for the content of this tab when selected, and the color
 * of the ripple.
 * @param unselectedContentColor the color for the content of this tab when not selected
 *
 */
@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: @Composable (() -> Unit),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = YdsTheme.colors.bottomBarSelected,
    unselectedContentColor: Color = YdsTheme.colors.bottomBarNormal,
) {
    val color = if (selected) selectedContentColor else unselectedContentColor

    CompositionLocalProvider(
        LocalContentColor provides color.copy(alpha = 1f),
    ) {
        Column(
            modifier = modifier
                .selectable(
                    selected = selected,
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClick = onClick
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabLayout(
                text = text,
            )
        }
    }
}

/**
 * A [Layout] that positions [text]
 *
 */
@Composable
fun TabLayout(
    text: @Composable (() -> Unit)
) {
    Layout(
        content = {
            text()
        },
        measurePolicy = { measurables, constraints ->
            val placeableText = measurables
                .first()
                .measure(
                    constraints
                )

            val tabWidth = placeableText.width

            val tabHeight = TabHeight.roundToPx()

            layout(width = tabWidth, height = tabHeight) {
                val contentY = (tabHeight - placeableText.height) / 2
                placeableText.placeRelative(0, contentY)
            }
        }
    )
}

private val TabHeight = 48.dp


@Preview(backgroundColor = 0xFFFFFFFF, showSystemUi = true, showBackground = true)
@Composable
private fun PreviewTab() {
    Row {
        Tab(
            selected = true,
            onClick = {
            },
            text = {
                YdsText(
                    text = "선택됨",
                    style = YdsTheme.typography.button2
                )
            },
        )
        Tab(
            selected = false,
            onClick = {
            },
            text = {
                YdsText(
                    text = "선택안됨",
                    style = YdsTheme.typography.button2
                )
            },
        )
    }
}