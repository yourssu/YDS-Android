package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.AnimationSpec
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
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.Duration
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsInAndOutEasing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
data class BottomSheetState(
    val modalBottomSheetState: ModalBottomSheetState,
    private val coroutineScope: CoroutineScope
) {
    fun show() = coroutineScope.launch {
        modalBottomSheetState.show()
    }

    fun hide() = coroutineScope.launch {
        modalBottomSheetState.hide()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetState(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.HalfExpanded,
    animationSpec: AnimationSpec<Float> = tween(
        durationMillis = Duration.Medium.millis,
        easing = YdsInAndOutEasing
    ),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = initialValue,
        animationSpec = animationSpec
    ),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): BottomSheetState = remember {
    BottomSheetState(
        modalBottomSheetState = modalBottomSheetState,
        coroutineScope = coroutineScope
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheet(
    sheetState: BottomSheetState,
    enableScroll: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
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
                        rememberScrollState(),
                        enabled = enableScroll
                    ),
                content = content
            )
        },
        sheetState = sheetState.modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = YdsTheme.colors.bgNormal,
        scrimColor = YdsTheme.colors.dimNormal
    ) {}

    BackHandler {
        sheetState.hide()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun BottomSheetPreview() {
    val sheetState = rememberBottomSheetState()

    YdsTheme {
        Column {
            Button(onClick = { sheetState.show() }) {
                Text(text = "Show")
            }
        }
        BottomSheet(sheetState = sheetState) {
            repeat(15) {
                ListItem(
                    onClick = { /*TODO*/ },
                    state = rememberListItemState(text = "hello $it")
                )
            }
        }
    }
}