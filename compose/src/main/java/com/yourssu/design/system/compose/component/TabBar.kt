package com.yourssu.design.system.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.Direction
import com.yourssu.design.system.compose.atom.Thickness
import com.yourssu.design.system.compose.base.Surface
import com.yourssu.design.system.compose.base.YdsScaffold
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.component.TabRowDefaults.tabIndicatorOffset


enum class TabBarSelectedState {
    SELECTED,
    DESELECTED
}


@Composable
fun TabBar(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        YdsText(text = "TabBar")
    }
}

@Composable
fun FixedTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    indicator: @Composable @UiComposable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    },
    divider: @Composable @UiComposable () -> Unit = @Composable {
        TabRowDefaults.Divider()
    },
    tabs: @Composable @UiComposable () -> Unit
) {
    Surface(
        modifier = modifier.selectableGroup(),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        SubcomposeLayout(Modifier.fillMaxWidth()) { constraints ->
            val tabRowWidth = constraints.maxWidth
            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)
            val tabCount = tabMeasurables.size
            val tabWidth = (tabRowWidth / tabCount)
            val tabPlaceables = tabMeasurables.map {
                it.measure(constraints.copy(minWidth = tabWidth, maxWidth = tabWidth))
            }

            val tabRowHeight = tabPlaceables.maxByOrNull { it.height }?.height ?: 0

            val tabPositions = List(tabCount) { index ->
                TabPosition(tabWidth.toDp() * index, tabWidth.toDp())
            }



            layout(tabRowWidth, tabRowHeight) {
                tabPlaceables.forEachIndexed { index, placeable ->
                    placeable.placeRelative(index * tabWidth, 0)
                }

                subcompose(TabSlots.Divider, divider).forEach {
                    val placeable = it.measure(constraints.copy(minHeight = 0))
                    placeable.placeRelative(0, tabRowHeight - placeable.height)
                }

                subcompose(TabSlots.Indicator) {
                    indicator(tabPositions)
                }.forEach {
                    it.measure(Constraints.fixed(tabRowWidth, tabRowHeight)).placeRelative(0, 0)
                }
            }
        }
    }
}

private enum class TabSlots {
    Tabs,
    Divider,
    Indicator
}

/**
 * Data class that contains information about a tab's position on screen, used for calculating
 * where to place the indicator that shows which tab is selected.
 *
 * @property left the left edge's x position from the start of the [TabRow]
 * @property right the right edge's x position from the start of the [TabRow]
 * @property width the width of this tab
 */
@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp) {
    val right: Dp get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        return result
    }

    override fun toString(): String {
        return "TabPosition(left=$left, right=$right, width=$width)"
    }
}


/**
 * Contains default implementations and values used for TabRow.
 */
object TabRowDefaults {
    /**
     * Default [Divider], which will be positioned at the bottom of the [TabRow], underneath the
     * indicator.
     *
     * @param modifier modifier for the divider's layout
     * @param thickness thickness of the divider
     * @param color color of the divider
     */
    @Composable
    fun Divider(
        direction: Direction = Direction.Horizontal,
        thickness: Thickness = Thickness.Thin
    ) {
        com.yourssu.design.system.compose.atom.Divider(
            direction = direction,
            thickness = thickness
        )
    }

    /**
     * Default indicator, which will be positioned at the bottom of the [TabRow], on top of the
     * divider.
     *
     * @param modifier modifier for the indicator's layout
     * @param height height of the indicator
     * @param color color of the indicator
     */
    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        height: Dp = IndicatorHeight,
        color: Color = YdsTheme.colors.bottomBarSelected
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .height(height)
                .background(color = color)
        )
    }

    /**
     * [Modifier] that takes up all the available width inside the [TabRow], and then animates
     * the offset of the indicator it is applied to, depending on the [currentTabPosition].
     *
     * @param currentTabPosition [TabPosition] of the currently selected tab. This is used to
     * calculate the offset of the indicator this modifier is applied to, as well as its width.
     */
    fun Modifier.tabIndicatorOffset(
        currentTabPosition: TabPosition
    ): Modifier = composed(
        inspectorInfo = debugInspectorInfo {
            name = "tabIndicatorOffset"
            value = currentTabPosition
        }
    ) {
        val currentTabWidth = currentTabPosition.width
        val indicatorOffset = currentTabPosition.left

        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth)
    }


    /**
     * Default height for [Indicator]
     */
    val IndicatorHeight: Dp = 2.dp

    /**
     * The default padding from the starting edge before a tab in a [ScrollableTabRow].
     */
    val ScrollableTabRowPadding: Dp = 52.dp
}


@Composable
fun ScrollableTabBar(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        YdsText(text = "TabBar")
    }
}


@Preview(name = "TabBar")
@Composable
private fun PreviewTabBar() {
    TabBar()
}

@Preview(name = "FixedTabBar")
@Composable
private fun PreviewFixedTabBar() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Home", "About", "Settings")

    YdsScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FixedTabBar(selectedTabIndex = tabIndex, modifier = Modifier) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                    )
                }
            }
        },
        bottomBar = {},
    ) {
        when (tabIndex) {
            0 -> Column {
                Text("0")
            }

            1 -> Column {
                Text("1")
            }

            2 -> Column {
                Text("2")
            }

        }
    }
}

@Preview(name = "ScrollableTabBar")
@Composable
private fun PreviewScrollableTabBar() {
    TabBar()
}