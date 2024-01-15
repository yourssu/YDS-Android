package com.yourssu.design.system.compose.atom

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.rule.Duration
import com.yourssu.design.system.compose.rule.YdsEasing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberYdsBottomSheetState(): ModalBottomSheetState {
    return rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = tween(
            durationMillis = Duration.Medium.millis,
            easing = YdsEasing
        ),
        skipHalfExpanded = true
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState = rememberYdsBottomSheetState(),
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                Modifier
                    .heightIn(
                        min = 88.dp,
                        max = LocalConfiguration.current.screenHeightDp.dp - 88.dp
                    )
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .verticalScroll(
                        state = rememberScrollState()
                    ),
                content = sheetContent
            )
        },
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = YdsTheme.colors.bgNormal,
        scrimColor = YdsTheme.colors.dimNormal,
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
private fun BottomSheetPreview() {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberYdsBottomSheetState()

    YdsTheme {
        BottomSheet(
            sheetContent = {
                repeat(100) {
                    ListItem(text = "$it", onClick = { /*TODO*/ })
                }
            },
            sheetState = sheetState
        ) {
            BoxButton(
                onClick = { coroutineScope.launch { sheetState.show() }},
                text = "open"
            )
        }
    }
}