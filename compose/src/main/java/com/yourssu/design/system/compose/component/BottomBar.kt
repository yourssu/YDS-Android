package com.yourssu.design.system.compose.component


import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.design.system.compose.atom.Divider
import com.yourssu.design.system.compose.atom.Thickness
import com.yourssu.design.system.compose.atom.TopBarButton
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.Surface
import com.yourssu.design.system.compose.base.YdsScaffold
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.component.topbar.TopBar
import com.yourssu.design.system.compose.rule.YdsInAndOutEasing

/**
 * 하단 네비게이션 바
 *
 * @param modifier Modifier
 * @param backgroundColor Color
 * @param contentColor Color
 * @param content RowScope.() -> Unit
 *
 * @see Screen
 * @see ScreenInfo
 */
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        modifier = modifier
            .fillMaxWidth()
            .height(BottomBarHeight),
    ) {
        Column {
            Divider(thickness = Thickness.Thin)
            Row(
                Modifier
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}


/**
 * 하단 네비게이션 바의 아이템
 *
 * @param modifier Modifier
 * @param selected Boolean
 * @param onClick () -> Unit
 * @param selectedIcon Int : 선택시 보여줄 아이콘
 * @param unselectedIcon Int : 선택되지 않았을 때 보여줄 아이콘
 * @param isImpactFeedbackEnabled Boolean : 선택시 햅틱 피드백 사용 여부
 * @param interactionSource MutableInteractionSource
 *
 * @see BottomBar
 * @see Screen
 * @see ScreenInfo
 */
@Composable
fun RowScope.BottomBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    isImpactFeedbackEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var isAnimating by remember { mutableStateOf(false) } // scale 변화 animation 중인지 여부
    val iconColor = YdsTheme.colors.run {
        if (selected) bottomBarSelected else bottomBarNormal
    }
    val haptic = LocalHapticFeedback.current // 햅틱 피드백

    val scale by animateFloatAsState(
        // scale 변화 animation 상태
        targetValue = if (isAnimating) 1.2f else 1f, // isAnimating 에 따라 scale 기본값(1f) ~ 1.2배 까지 변화
        animationSpec = tween(
            durationMillis = 25,
            easing = YdsInAndOutEasing,
        ),
        finishedListener = {// animating 끝나면 false로 변경. scale 1.2f -> 1f로 변화
            if (isAnimating) {
                isAnimating = false
            }
        },
        label = "",
    )

    Box(
        modifier = modifier
            .selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (isImpactFeedbackEnabled) { // 햅틱 피드백 사용 여부
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress) // 햅틱 피드백
                    }
                    isAnimating = true // scale 변화 animation 시작
                    onClick() // 클릭 이벤트
                },
                role = Role.Tab,
            )
            .height(BottomBarHeight)
            .weight(1f),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            id = if (selected) selectedIcon else unselectedIcon,
            iconSize = IconSize.Medium,
            tint = iconColor,
            modifier = Modifier.scale(scale),
        )
    }
}

// Preview below this line

@Composable
private fun ScreenA(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        YdsText("A 화면입니다", style = YdsTheme.typography.body1)
        BoxButton(
            text = "B로 가기",
            onClick = {
                navController.navigate(Screen.AboutB.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}

@Composable
private fun ScreenB(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        YdsText("B 화면입니다", style = YdsTheme.typography.body1)
        BoxButton(
            text = "C로 가기",
            onClick = {
                navController.navigate(Screen.AboutC.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}

@Composable
private fun ScreenC(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        YdsText("C 화면입니다", style = YdsTheme.typography.body1)
        BoxButton(
            text = "D로 가기",
            onClick = {
                navController.navigate(Screen.AboutD.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}

@Composable
private fun ScreenD(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        YdsText("D 화면입니다", style = YdsTheme.typography.body1)
        BoxButton(
            text = "E로 가기",
            onClick = {
                navController.navigate(Screen.AboutE.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}

@Composable
private fun ScreenE(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        YdsText("E 화면입니다", style = YdsTheme.typography.body1)
        BoxButton(
            text = "A로 가기",
            onClick = {
                navController.navigate(Screen.AboutA.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}


/**
 * 스크린 종류 정의
 * 해당 sealed class 를 바탕으로 라우팅
 */
sealed class Screen(val route: String) {
    object AboutA : Screen("A")
    object AboutB : Screen("B")
    object AboutC : Screen("C")
    object AboutD : Screen("D")
    object AboutE : Screen("E")
}

data class ScreenInfo(
    val screen: Screen,
    val selectedIcon: Int,
    val unselectedIcon: Int,
)

@Preview
@Composable
private fun PreviewNavigation() {
    val navController = rememberNavController()

    val items = listOf(
        ScreenInfo(Screen.AboutA, R.drawable.ic_home_filled, R.drawable.ic_home_line),
        ScreenInfo(Screen.AboutB, R.drawable.ic_board_filled, R.drawable.ic_board_line),
        ScreenInfo(Screen.AboutC, R.drawable.ic_calendar_filled, R.drawable.ic_calendar_line),
        ScreenInfo(Screen.AboutD, R.drawable.ic_rank_filled, R.drawable.ic_rank_line),
        ScreenInfo(Screen.AboutE, R.drawable.ic_person_filled, R.drawable.ic_person_line),
    )

    YdsScaffold(
        topBar = {
            TopBar(
                title = "타이틀",
                navigationIcon = {
                    TopBarButton(
                        icon = R.drawable.ic_arrow_left_line,
                        isDisabled = false,
                    )
                },
                actions = {
                    TopBarButton(
                        icon = R.drawable.ic_bell_line,
                        isDisabled = false,
                    )
                    TopBarButton(
                        icon = R.drawable.ic_search_line,
                        isDisabled = false,
                        onClick = { },
                    )
                },
            )
        },
        bottomBar = {
            BottomBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screenInfo ->
                    BottomBarItem(
                        selectedIcon = screenInfo.selectedIcon,
                        unselectedIcon = screenInfo.unselectedIcon,
                        selected = currentDestination?.hierarchy?.any { it.route == screenInfo.screen.route } == true,
                        onClick = {
                            navController.navigate(screenInfo.screen.route) {
                                // 유저가 선택한 아이템(화면)으로 인해 백스택에 쌓이는 것을 방지하기 위해
                                // 그래프의 시작점(여기선 ScreenA)으로 popUpTo(백스택 ScreenA까지 pop)
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // 선택한 아이템이 현재 선택된 아이템과 같을 경우, 새로운 destination 를 생성하지 않고
                                // 기존 destination를 재사용
                                // saveState 및 restoreState 플래그를 사용하면 하단 탐색 항목 간에 전환할 때 항목의 상태와 백 스택이 올바르게 저장되고 복원
                                launchSingleTop = true
                                // 선택한 아이템이 현재 선택된 아이템과 같을 경우, 해당 아이템의 상태를 복원
                                restoreState = true
                            }
//                            for (i in navController.backQueue) {
//                                println("backQueue: ${i.destination.route}")
//                            } => 쌓인 화면들을 확인할때 사용
                        },
                    )
                }
            }
        },
        content = {
            NavHost(
                navController,
                startDestination = Screen.AboutA.route,
                Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                composable(Screen.AboutA.route) { ScreenA(navController) }
                composable(Screen.AboutB.route) { ScreenB(navController) }
                composable(Screen.AboutC.route) { ScreenC(navController) }
                composable(Screen.AboutD.route) { ScreenD(navController) }
                composable(Screen.AboutE.route) { ScreenE(navController) }
            }
        },
    )
}

private val BottomBarHeight = 56.dp