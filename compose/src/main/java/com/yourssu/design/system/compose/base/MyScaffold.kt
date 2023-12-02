package com.yourssu.design.system.compose.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.foundation.LocalContentColor

@Stable
class MyScaffoldState(
    val snackbarHostState: SnackbarHostState
)

@Composable
fun rememberMyScaffoldState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): MyScaffoldState = remember {
    MyScaffoldState(snackbarHostState)
}

@Composable
fun MyScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: MyScaffoldState = rememberMyScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    backgroundColor: Color = YdsTheme.colors.bgNormal,
    contentColor: Color = LocalContentColor.current,
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        MyScaffoldLayout(
            topBar = topBar,
            content = content,
            snackbar = {
                snackbarHost(scaffoldState.snackbarHostState)
            },
            bottomBar = bottomBar
        )
    }
}

@Composable
private fun MyScaffoldLayout(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    snackbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val topBarPlacesables = subcompose(MyScaffoldLayoutContent.TopBar, topBar).map {
                it.measure(looseConstraints)
            }
            val topBarHeight = topBarPlacesables.maxByOrNull { it.height }?.height ?: 0

            val snackbarPlaceables = subcompose(MyScaffoldLayoutContent.Snackbar, snackbar).map {
                it.measure(looseConstraints)
            }
            val snackbarHeight = snackbarPlaceables.maxByOrNull { it.height }?.height ?: 0

            val bottomBarPlaceables = subcompose(MyScaffoldLayoutContent.BottomBar, bottomBar).map {
                it.measure(looseConstraints)
            }
            val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0

            val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
                snackbarHeight + bottomBarHeight
            } else {
                0
            }

            val bodyContentHeight = layoutHeight - topBarHeight

            val contentPlaceables = subcompose(MyScaffoldLayoutContent.MainContent) {
                val innerPadding = PaddingValues(
                    bottom = bottomBarHeight.toDp()
                )
                content(innerPadding)
            }.map {
                it.measure(looseConstraints.copy(maxHeight = bodyContentHeight))
            }

            contentPlaceables.forEach {
                it.place(0, topBarHeight)
            }
            topBarPlacesables.forEach {
                it.place(0, 0)
            }
            snackbarPlaceables.forEach {
                it.place(0, layoutHeight - snackbarOffsetFromBottom)
            }
            bottomBarPlaceables.forEach {
                it.place(0, layoutHeight - bottomBarHeight)
            }
        }
    }
}

private enum class MyScaffoldLayoutContent { TopBar, MainContent, Snackbar, BottomBar }
