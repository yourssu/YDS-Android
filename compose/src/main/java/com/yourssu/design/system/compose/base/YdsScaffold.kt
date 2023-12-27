package com.yourssu.design.system.compose.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.foundation.LocalYdsContentColor

@Composable
fun YdsScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    backgroundColor: Color = YdsTheme.colors.bgNormal,
    contentColor: Color = LocalYdsContentColor.current,
    content: @Composable () -> Unit
) {
    // TODO: Toast 추가
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            topBar()
            Box(Modifier.weight(1f)) {
                content()
            }
            bottomBar()
        }
    }
}
