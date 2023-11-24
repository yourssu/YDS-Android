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
 * 다양한 네비게이션 메뉴가 포함된 [FixedTabBar]입니다. 보통 상단에 사용됩니다.
 *
 * [FixedTabBar]에는 [Tab] 행이 포함되어 있으며 현재 선택된 탭 아래에 인디케이터가 표시됩니다.
 * [FixedTabBar]는 전체 행을 따라 균등한 간격으로 탭을 배치하며 각 탭은 동일한 공간을 차지합니다.
 * 동일한 크기를 적용하지 않고 스크롤할 수 있는 탭바에 대해서는 [ScrollableTabBar] 참조하세요.
 *
 * * 스크롤되지 않습니다. 각 탭의 너비는 전체 너비의 1/n입니다.
 * 최소 2개, 최대 5개 탭을 사용해주세요.
 *
 * * 기기에 따른 변화는 없습니다.
 *
 * @param selectedTabIndex 현재 선택된 탭의 인덱스
 * @param modifier 이 TabBar에 대한 선택적 Modifier
 * @param backgroundColor TabBar의 배경색입니다.
 * @param contentColor TabBar의 콘텐츠 색상입니다.
 * @param tabs 이 TabBar 내부의 탭입니다. 일반적으로 이는 여러 개의 [Tab]이 됩니다. 각 요소
 * 이 람다 내부에서는 측정되어 TabBar 전체에 균등하게 배치됩니다. 각 항목은 동일한 크기를 차지합니다.
 *
 */
@Composable
fun FixedTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
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

                require(tabCount in 2..5) {
                    "Please use a minimum of 2 and a maximum of 5 tabs."
                }

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
                            TabBarDefaults.Divider()
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
                            TabBarDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                            )
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
 *
 * ScrollableTabBar에는 [Tab] 행이 포함되어 있으며 현재 탭 아래에 인디케이터가 표시됩니다.
 * ScrollableTabBar는 시작 가장자리에서 탭 오프셋을 배치하고 다음을 허용합니다.
 * 화면 밖에 있는 탭으로 스크롤합니다. 허용하지 않는 고정 탭 행의 경우
 * 스크롤하고 탭을 균등하게 배치하려면 [FixedTabBar]를 참조하세요.
 *
 * * 스크롤됩니다. 각 탭의 너비는 88로 고정되어 있습니다.
 * 최소/최대 탭 수에는 제한이 없습니다.
 * * 모바일 기기의 경우 첫 번째 탭 왼쪽 및 마지막 탭의 오른쪽에 16의 여백이 있습니다.
 *
 * @param selectedTabIndex 현재 선택된 탭의 인덱스
 * @param modifier 선택적 [Modifier]입니다.
 * @param backgroundColor ScrollableTabBar의 배경색입니다.
 * @param contentColor 이 ScrollableTabBar가 제공하는 기본 콘텐츠 색상입니다.
 * @param tabs 이 ScrollableTabBar 내부의 탭들입니다. 일반적으로 이는 여러 개의 [Tab]이 됩니다.
 */
@Composable
fun ScrollableTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    tabs: @Composable @UiComposable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
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
            val minTabWidth = TabBarDefaults.ScrollableTabWidth.roundToPx()
            val edgePadding = TabBarDefaults.ScrollableTabPadding.roundToPx()
            val tabConstraints = constraints.copy(minWidth = minTabWidth)

            val placeableTabs = subcompose(
                slotId = TabSlots.Tabs,
                content = {
                    tabs()
                }
            ).map { it.measure(tabConstraints) }

            var tabBarWidth = edgePadding * 2
            val tabBarHeight = placeableTabs.first().height

            placeableTabs.forEach {
                tabBarWidth += it.width
            }

            // Position the children.
            layout(tabBarWidth, tabBarHeight) {
                // Place the tabs
                val tabPositions = mutableListOf<TabPosition>()
                var left = edgePadding

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
                        TabBarDefaults.Divider()
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
                        TabBarDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                        )
                    }
                ).forEach {
                    val placeableIndicator = it.measure(
                        Constraints.fixed(tabBarWidth, tabBarHeight)
                    )
                    placeableIndicator.placeRelative(0, 0)
                }

                scrollableTabData.onLaidOut(
                    density = this@SubcomposeLayout,
                    edgeOffset = edgePadding,
                    tabPositions = tabPositions,
                    selectedTab = selectedTabIndex
                )
            }
        }
    }
}


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

    /**
     * The default width of a [Tab] when tab is in [ScrollableTabBar].
     */
    val ScrollableTabWidth: Dp = 88.dp
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
                        text = title,
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
                        text = title,
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