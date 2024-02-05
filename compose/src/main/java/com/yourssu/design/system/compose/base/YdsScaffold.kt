package com.yourssu.design.system.compose.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.design.system.compose.atom.TopBarButton
import com.yourssu.design.system.compose.component.toast.ToastDuration
import com.yourssu.design.system.compose.component.toast.ToastHost
import com.yourssu.design.system.compose.component.toast.ToastHostState
import com.yourssu.design.system.compose.component.toast.rememberToastHostState
import com.yourssu.design.system.compose.component.topbar.TopBar
import com.yourssu.design.system.compose.foundation.LocalYdsContentColor
import kotlinx.coroutines.launch

private enum class ScaffoldLayoutContent { TopBar, MainContent, Snackbar, BottomBar }

@Composable
fun YdsScaffold(
    modifier: Modifier = Modifier,
    toastHostState: ToastHostState = rememberToastHostState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    toastHost: @Composable (ToastHostState) -> Unit = { ToastHost(it) },
    backgroundColor: Color = YdsTheme.colors.bgNormal,
    contentColor: Color = LocalYdsContentColor.current,
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(modifier = modifier, color = backgroundColor, contentColor = contentColor) {
        ScaffoldLayout(
            topBar = topBar,
            bottomBar = bottomBar,
            toast = { toastHost(toastHostState) },
            content = content,
        )
    }
}

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    toast: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val pxValue = LocalDensity.current.run { ToastApartFromBottom.toPx() }.toInt()

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {

            val topBarPlaceables = subcompose(ScaffoldLayoutContent.TopBar, topBar).map {
                it.measure(looseConstraints)
            }


            val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0

            val toastPlaceables = subcompose(ScaffoldLayoutContent.Snackbar, toast).map {
                it.measure(looseConstraints)
            }

            val toastHeight = toastPlaceables.maxByOrNull { it.height }?.height ?: 0

            val bottomBarPlaceables = subcompose(ScaffoldLayoutContent.BottomBar, bottomBar).map {
                it.measure(looseConstraints)
            }

            val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0

            val snackbarOffsetFromBottom = if (toastHeight != 0) {
                toastHeight + pxValue
            } else {
                0
            }

            val bodyContentHeight = layoutHeight - topBarHeight

            val bodyContentPlaceables = subcompose(ScaffoldLayoutContent.MainContent) {
                val innerPadding = PaddingValues(bottom = bottomBarHeight.toDp())
                content(innerPadding)
            }.map { it.measure(looseConstraints.copy(maxHeight = bodyContentHeight)) }

            bodyContentPlaceables.forEach {
                it.place(0, topBarHeight)
            }
            topBarPlaceables.forEach {
                it.place(0, 0)
            }
            toastPlaceables.forEach {
                it.place(0, layoutHeight - snackbarOffsetFromBottom)
            }
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.forEach {
                it.place(0, layoutHeight - bottomBarHeight)
            }
        }
    }
}

private val ToastApartFromBottom = 72.dp


@Preview
@Composable
private fun YdsScaffoldPreview() {
    val toastHostState: ToastHostState = rememberToastHostState()
    val scope = rememberCoroutineScope()

    YdsScaffold(
        toastHostState = toastHostState,
        topBar = {
            TopBar(
                title = "타이틀",
                navigationIcon = {
                    TopBarButton(
                        icon = R.drawable.ic_arrow_left_line,
                    )
                },
                actions = {
                    TopBarButton(
                        icon = R.drawable.ic_bell_line,
                    )
                    TopBarButton(
                        icon = R.drawable.ic_search_line,
                        onClick = {
                            scope.launch {
                                toastHostState.showToast(
                                    message = "Snackbar",
                                    duration = ToastDuration.SHORT,
                                )
                            }
                        },
                    )
                },
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                YdsText(
                    text = "This is 72.dp",
                    style = YdsTheme.typography.body1,
                    color = Color.White,
                )
            }
        },
        toastHost = { ToastHost(it) },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                YdsText(text = "content", style = YdsTheme.typography.body1)
                BoxButton(
                    onClick = {
                        scope.launch {
                            toastHostState.showToast(
                                message = "잠, 아스라히 노새, 멀듯이, 흙으로 봅니다.",
                                duration = ToastDuration.SHORT,
                            )
                        }
                    },
                    text = "짧은 토스트 버튼",
                )
                BoxButton(
                    onClick = {
                        scope.launch {
                            toastHostState.showToast(
                                message = "잠, 아스라히 노새, 멀듯이, 흙으로 봅니다. 때 불러 가슴속에 나의 풀이 이름과 언덕 오면 가을 봅니다.",
                                duration = ToastDuration.LONG,
                            )
                        }
                    },
                    text = "긴 토스트 버튼",
                )
            }
        },
    )
}

