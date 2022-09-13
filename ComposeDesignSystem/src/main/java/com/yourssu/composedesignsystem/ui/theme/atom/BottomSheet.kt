package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.Duration
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsInAndOutEasing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
data class BottomSheetState(
    val modalBottomSheetState: ModalBottomSheetState
) {
    var currentValue by mutableStateOf(modalBottomSheetState.currentValue)

    fun show() {
        currentValue = ModalBottomSheetValue.Expanded
    }

    fun hide() {
        currentValue = ModalBottomSheetValue.Hidden
    }
}

// rememberSavable
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetState(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Expanded,
    animationSpec: AnimationSpec<Float> = tween(
        durationMillis = Duration.Short.millis,
        easing = YdsInAndOutEasing
    ),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = initialValue,
        animationSpec = animationSpec
    )
): BottomSheetState = remember {
    BottomSheetState(modalBottomSheetState = modalBottomSheetState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheet(
    sheetState: BottomSheetState,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 88.dp,
                        max = LocalConfiguration.current.screenHeightDp.dp - 88.dp
                    )
                    .padding(vertical = 20.dp),
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

    LaunchedEffect(sheetState.currentValue) {
        when (sheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> sheetState.modalBottomSheetState.hide()
            ModalBottomSheetValue.Expanded -> sheetState.modalBottomSheetState.show()
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun BottomSheetPreview() {
    val sheetState = rememberBottomSheetState()
    YdsTheme {
        // Show 안 먹힘
        Button(onClick = { sheetState.show() }) {
            Text(text = "Show")
        }
        BottomSheet(sheetState = sheetState) {
            repeat(10) {
                ListItem(onClick = { /*TODO*/ }, state = rememberListItemState(text = "hello $it"))
            }
        }
    }
}