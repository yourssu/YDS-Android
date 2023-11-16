package com.yourssu.design.system.compose.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.Direction
import com.yourssu.design.system.compose.atom.Thickness
import com.yourssu.design.system.compose.base.Surface
import com.yourssu.design.system.compose.base.YdsScaffold
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.component.TabBarDefaults.tabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * This is [FixedTabBar] containing various navigation menus. Usually used at the top.
 *
 * A [FixedTabBar] contains a row of [Tab]s, and displays an indicator underneath the currently
 * selected tab. A TabRow places its tabs evenly spaced along the entire row, with each tab
 * taking up an equal amount of space. See [ScrollableTabBar] for a tab row that does not enforce
 * equal size, and allows scrolling to tabs that do not fit on screen.
 *
 * As well as customizing the tab, you can also provide a custom [indicator], to customize
 * the indicator displayed for a tab. [indicator] will be placed to fill the entire TabBar, so it
 * should internally take care of sizing and positioning the indicator to match changes to
 * [selectedTabIndex].
 *
 * * It doesn't scroll. The width of each tab is 1/n of the total width.
 * Please use a minimum of 2 and a maximum of 5 tabs.
 *
 * * There is no change depending on the device.
 *
 * @param selectedTabIndex the index of the currently selected tab
 * @param modifier optional [Modifier] for this TabBar
 * @param backgroundColor The background color for the TabBar. Use [Color.Transparent] to have
 * no color.
 * @param contentColor The preferred content color provided by this TabBar to its children.
 * Defaults to either the matching content color for [backgroundColor], or if [backgroundColor] is
 * not a color from the theme, this will keep the same value set above this TabBar.
 * @param indicator the indicator that represents which tab is currently selected. By default this
 * will be a [TabBarDefaults.Indicator], using a [TabBarDefaults.tabIndicatorOffset]
 * modifier to animate its position. Note that this indicator will be forced to fill up the
 * entire TabBar, so you should use [TabBarDefaults.tabIndicatorOffset] or similar to
 * animate the actual drawn indicator inside this space, and provide an offset from the start.
 * @param divider the divider displayed at the bottom of the TabBar. This provides a layer of
 * separation between the TabRow and the content displayed underneath.
 * @param tabs the tabs inside this TabBar. Typically this will be multiple [Tab]s. Each element
 * inside this lambda will be measured and placed evenly across the TabRow, each taking up equal
 * space.
 */
@Composable
fun FixedTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    indicator: @Composable @UiComposable (tabPositions: List<TabPosition>) -> Unit =
        @Composable { tabPositions ->
            TabBarDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
    divider: @Composable @UiComposable () -> Unit = @Composable {
        TabBarDefaults.Divider()
    },
    tabs: @Composable @UiComposable () -> Unit
) {
    Surface(
        modifier = modifier
            .selectableGroup(),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        SubcomposeLayout(
            modifier = Modifier.fillMaxWidth(),
            measurePolicy = { constraints ->
                val tabBarWidth = constraints.maxWidth
                val measurableTabs = subcompose(
                    slotId = TabSlots.Tabs,
                    content = tabs
                )
                val tabCount = measurableTabs.size
                val tabWidth = (tabBarWidth / tabCount)
                val placeableTabs = measurableTabs.map {
                    it.measure(
                        constraints.copy(minWidth = tabWidth, maxWidth = tabWidth)
                    )
                }

                val tabBarHeight = placeableTabs.first().height

                val tabPositions = List(tabCount) { index ->
                    TabPosition(
                        left = tabWidth.toDp() * index,
                        width = tabWidth.toDp()
                    )
                }

                layout(tabBarWidth, tabBarHeight) {
                    placeableTabs.forEachIndexed { index, placeable ->
                        placeable.placeRelative(index * tabWidth, 0)
                    }

                    subcompose(
                        slotId = TabSlots.Divider,
                        content = {
                            divider()
                        }
                    ).forEach {
                        val placeableDivider = it.measure(
                            constraints.copy(
                                minWidth = tabBarWidth,
                                maxWidth = tabBarWidth
                            )
                        )
                        placeableDivider.placeRelative(0, tabBarHeight - placeableDivider.height)
                    }

                    subcompose(
                        slotId = TabSlots.Indicator,
                        content = {
                            indicator(tabPositions)
                        }
                    ).forEach {
                        val placeableIndicator = it.measure(
                            Constraints.fixed(tabBarWidth, tabBarHeight)
                        )
                        placeableIndicator.placeRelative(0, 0)
                    }
                }
            }
        )
    }
}

/**
 * When a set of tabs cannot fit on screen, use scrollable tabs.
 * They are best used for browsing on touch interfaces.
 *
 * A ScrollableTabRow contains a row of [Tab]s, and displays an indicator underneath the currently
 * selected tab. A ScrollableTabBar places its tabs offset from the starting edge, and allows
 * scrolling to tabs that are placed off screen. For a fixed tab row that does not allow
 * scrolling, and evenly places its tabs, see [FixedTabBar].
 *
 * * It scrolls. The width of each tab is fixed at 88.
 * There is no limit to the minimum/maximum number of tabs.
 * * If the device is mobile, there is a margin of 16 to the left of the first tab
 * and to the right of the last tab.
 *
 * @param selectedTabIndex the index of the currently selected tab
 * @param modifier optional [Modifier] for this ScrollableTabRow
 * @param backgroundColor The background color for the ScrollableTabBar. Use [Color.Transparent] to
 * have no color.
 * @param contentColor The preferred content color provided by this ScrollableTabBar to its
 * children. Defaults to either the matching content color for [backgroundColor], or if
 * [backgroundColor] is not a color from the theme, this will keep the same value set above this
 * ScrollableTabBar.
 * @param edgePadding the padding between the starting and ending edge of ScrollableTabBar, and
 * the tabs inside the ScrollableTabBar.
 * @param indicator the indicator that represents which tab is currently selected. By default this
 * will be a [TabBarDefaults.Indicator], using a [TabBarDefaults.tabIndicatorOffset]
 * modifier to animate its position. Note that this indicator will be forced to fill up the
 * entire ScrollableTabBar, so you should use [TabBarDefaults.tabIndicatorOffset] or similar to
 * animate the actual drawn indicator inside this space, and provide an offset from the start.
 * @param divider the divider displayed at the bottom of the ScrollableTabBar. This provides a layer
 * of separation between the ScrollableTabBar and the content displayed underneath.
 * @param tabs the tabs inside this ScrollableTabBar. Typically this will be multiple [Tab]s. Each
 * element inside this lambda will be measured and placed evenly across the TabBar, each taking
 * up equal space.
 */
@Composable
fun ScrollableTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    edgePadding: Dp = TabBarDefaults.ScrollableTabPadding,
    indicator: @Composable @UiComposable (tabPositions: List<TabPosition>) -> Unit =
        @Composable { tabPositions ->
            TabBarDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
    divider: @Composable @UiComposable () -> Unit = @Composable {
        TabBarDefaults.Divider()
    },
    tabs: @Composable @UiComposable () -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        val scrollableTabData = remember(scrollState, coroutineScope) {
            ScrollableTabData(
                scrollState = scrollState,
                coroutineScope = coroutineScope
            )
        }
        SubcomposeLayout(
            Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
                .horizontalScroll(scrollState)
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
            val minTabWidth = ScrollableTabWidth.roundToPx()
            val edgeRadding = edgePadding.roundToPx()
            val tabConstraints = constraints.copy(minWidth = minTabWidth)

            val placeableTabs = subcompose(
                slotId = TabSlots.Tabs,
                content = {
                    tabs()
                }
            ).map { it.measure(tabConstraints) }

            var tabBarWidth = edgeRadding * 2
            var tabBarHeight = placeableTabs.first().height

            placeableTabs.forEach {
                tabBarWidth += it.width
            }

            // Position the children.
            layout(tabBarWidth, tabBarHeight) {
                // Place the tabs
                val tabPositions = mutableListOf<TabPosition>()
                var left = edgeRadding

                placeableTabs.forEach {
                    it.placeRelative(left, 0)
                    tabPositions.add(
                        TabPosition(
                            left = left.toDp(),
                            width = it.width.toDp()
                        )
                    )
                    left += it.width
                }

                subcompose(
                    slotId = TabSlots.Divider,
                    content = {
                        divider()
                    }
                ).forEach {
                    val placeableDivider = it.measure(
                        constraints.copy(
                            minWidth = tabBarWidth,
                            maxWidth = tabBarWidth
                        )
                    )
                    placeableDivider.placeRelative(0, tabBarHeight - placeableDivider.height)
                }

                subcompose(
                    slotId = TabSlots.Indicator,
                    content = {
                        indicator(tabPositions)
                    }
                ).forEach {
                    val placeableIndicator = it.measure(
                        Constraints.fixed(tabBarWidth, tabBarHeight)
                    )
                    placeableIndicator.placeRelative(0, 0)
                }

                scrollableTabData.onLaidOut(
                    density = this@SubcomposeLayout,
                    edgeOffset = edgeRadding,
                    tabPositions = tabPositions,
                    selectedTab = selectedTabIndex
                )
            }
        }
    }
}

private val ScrollableTabWidth = 88.dp


/**
 * Class holding onto state needed for [ScrollableTabBar]
 */
private class ScrollableTabData(
    private val scrollState: ScrollState,
    private val coroutineScope: CoroutineScope
) {
    private var selectedTab: Int? = null

    fun onLaidOut(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>,
        selectedTab: Int
    ) {
        // Animate if the new tab is different from the old tab, or this is called for the first
        // time (i.e selectedTab is `null`).
        if (this.selectedTab != selectedTab) {
            this.selectedTab = selectedTab
            tabPositions.getOrNull(selectedTab)?.let {
                // Scrolls to the tab with [tabPosition], trying to place it in the center of the
                // screen or as close to the center as possible.
                val calculatedOffset = it.calculateTabOffset(density, edgeOffset, tabPositions)
                if (scrollState.value != calculatedOffset) {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            calculatedOffset,
                            animationSpec = ScrollableTabRowScrollSpec
                        )
                    }
                }
            }
        }
    }

    /**
     * @return the offset required to horizontally center the tab inside this TabBar.
     * If the tab is at the start / end, and there is not enough space to fully centre the tab, this
     * will just clamp to the min / max position given the max width.
     */
    private fun TabPosition.calculateTabOffset(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>
    ): Int = with(density) {
        val totalTabRowWidth = tabPositions.last().right.roundToPx() + edgeOffset
        val visibleWidth = totalTabRowWidth - scrollState.maxValue
        val tabOffset = left.roundToPx()
        val scrollerCenter = visibleWidth / 2
        val tabWidth = width.roundToPx()
        val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
        // How much space we have to scroll. If the visible width is <= to the total width, then
        // we have no space to scroll as everything is always visible.
        val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
        return centeredTabOffset.coerceIn(0, availableSpace)
    }
}

private enum class TabSlots {
    Tabs,
    Divider,
    Indicator
}

/**
 * [AnimationSpec] used when scrolling to a tab that is not fully visible.
 */
private val ScrollableTabRowScrollSpec: AnimationSpec<Float> = tween(
    durationMillis = 250,
    easing = FastOutSlowInEasing
)

/**
 * Data class that contains information about a tab's position on screen, used for calculating
 * where to place the indicator that shows which tab is selected.
 *
 * @property left the left edge's x position from the start of the TabBar
 * @property right the right edge's x position from the start of the TabBar
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
 * Contains default implementations and values used for TabBar.
 */
object TabBarDefaults {
    /**
     * Default [Divider], which will be positioned at the bottom of the TabBar, underneath the
     * indicator.
     *
     * @param direction direction of the divider
     * @param thickness thickness of the divider
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
     * Default indicator, which will be positioned at the bottom of the TabBar, on top of the
     * divider.
     *
     * @param modifier modifier for the indicator's layout
     * @param height height of the indicator
     * @param color color of the indicator
     */
    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        height: Dp = 2.dp,
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
     * [Modifier] that takes up all the available width inside the TabBar, and then animates
     * the offset of the indicator it is applied to, depending on the [currentTabPosition].
     *
     * @param currentTabPosition [TabPosition] of the currently selected tab. This is used to
     * calculate the offset of the indicator this modifier is applied to, as well as its width.
     */
    fun Modifier.tabIndicatorOffset(
        currentTabPosition: TabPosition
    ): Modifier = composed {
        val currentTabWidth = currentTabPosition.width
        val indicatorOffset = currentTabPosition.left

        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth)
    }

    /**
     * The default padding from the starting edge before a tab in a [ScrollableTabBar].
     */
    val ScrollableTabPadding: Dp = 16.dp
}

@Preview(name = "FixedTabBar")
@Composable
private fun PreviewFixedTabBar() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("선택됨", "선택됨", "선택됨", "선택됨")

    YdsScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FixedTabBar(selectedTabIndex = tabIndex, modifier = Modifier) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { YdsText(title, style = YdsTheme.typography.button2) },
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
                YdsText("0", style = YdsTheme.typography.button2)
            }

            1 -> Column {
                YdsText("1", style = YdsTheme.typography.button2)
            }

            2 -> Column {
                YdsText("2", style = YdsTheme.typography.button2)
            }

            3 -> Column {
                YdsText("3", style = YdsTheme.typography.button2)
            }
        }
    }
}

@Preview(name = "ScrollableTabBar")
@Composable
private fun PreviewScrollableTabBar() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("선택됨", "선택됨", "선택됨", "선택됨", "선택됨", "선택됨")

    YdsScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ScrollableTabBar(selectedTabIndex = tabIndex, modifier = Modifier) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { YdsText(title, style = YdsTheme.typography.button2) },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        },
                    )
                }
            }
        },
        bottomBar = {},
    ) {
        when (tabIndex) {
            0 -> Column {
                YdsText("0", style = YdsTheme.typography.button2)
            }

            1 -> Column {
                YdsText("1", style = YdsTheme.typography.button2)
            }

            2 -> Column {
                YdsText("2", style = YdsTheme.typography.button2)
            }

            3 -> Column {
                YdsText("3", style = YdsTheme.typography.button2)
            }
        }
    }
}