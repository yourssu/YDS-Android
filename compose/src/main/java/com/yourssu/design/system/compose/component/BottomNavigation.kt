package com.yourssu.design.system.compose.component


import android.util.Log
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
import com.yourssu.design.system.compose.component.toast.ToastHost
import com.yourssu.design.system.compose.component.topbar.TopBar
import com.yourssu.design.system.compose.rule.YdsInAndOutEasing

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        modifier = modifier
            .fillMaxWidth()
            .height(BottomNavigationHeight)
    ) {
        Column {
            Divider(thickness = Thickness.Thin)
            Row(
                Modifier
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    isImpactFeedbackEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var isAnimating by remember { mutableStateOf(false) }
    val iconColor = YdsTheme.colors.run {
        if (selected) bottomBarSelected else bottomBarNormal
    }
    val haptic = LocalHapticFeedback.current

    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.2f else 1f,
        animationSpec = tween(
            durationMillis = 25,
            easing = YdsInAndOutEasing
        ),
        finishedListener = {
            if (isAnimating) {
                isAnimating = false
            }
        },
        label = ""
    )

    Box(
        modifier = modifier
            .selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (isImpactFeedbackEnabled) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    isAnimating = true
                    onClick()
                },
                role = Role.Tab
            )
            .height(BottomNavigationHeight)
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            id = if (selected) selectedIcon else unselectedIcon,
            iconSize = IconSize.Medium,
            tint = iconColor,
            modifier = Modifier.scale(scale)
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
                navController.navigate(Screen.AboutB.route)
            }
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
                navController.navigate(Screen.AboutC.route)
            }
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
                navController.navigate(Screen.AboutD.route)
            }
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
                navController.navigate(Screen.AboutE.route)
            }
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
                navController.navigate(Screen.AboutA.route)
            }
        )
    }
}


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
fun PreviewNavigation() {
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
                        onClick = { }
                    )
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screenInfo ->
                    BottomNavigationItem(
                        selectedIcon = screenInfo.selectedIcon,
                        unselectedIcon = screenInfo.unselectedIcon,
                        selected = currentDestination?.hierarchy?.any { it.route == screenInfo.screen.route } == true,
                        onClick = {
                            navController.navigate(screenInfo.screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }

                            Log.d("KWK", "after ${navBackStackEntry.toString()}, $currentDestination")
                        }
                    )
                }
            }
        },
        toastHost = { ToastHost(it) },
        content = {
            NavHost(
                navController,
                startDestination = Screen.AboutA.route,
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                composable(Screen.AboutA.route) { ScreenA(navController) }
                composable(Screen.AboutB.route) { ScreenB(navController) }
                composable(Screen.AboutC.route) { ScreenC(navController) }
                composable(Screen.AboutD.route) { ScreenD(navController) }
                composable(Screen.AboutE.route) { ScreenE(navController) }
            }
        }
    )
}

private val BottomNavigationHeight = 56.dp
