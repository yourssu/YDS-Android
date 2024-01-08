package com.yourssu.design.system.compose.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.YdsText
import kotlinx.coroutines.CoroutineScope

@Composable
fun Picker(
    modifier: Modifier = Modifier,
    firstItemList: List<String>,
    secondItemList: List<String>? = null,
    thirdItemList: List<String>? = null,
    firstInitialIndex: Int = 0,
    secondInitialIndex: Int = 0,
    thirdInitialIndex: Int = 0,
    onFirstItemChange: ((index: Int) -> Unit)?,
    onSecondItemChange: ((index: Int) -> Unit)? = null,
    onThirdItemChange: ((index: Int) -> Unit)? = null,
) {
    val itemHeightDp: Dp = YdsTheme.typography.body1.lineHeight

    val totalItemHeightDp = itemHeightDp + 4.dp * 2 // 위 아래 패딩 4dp

    val itemLists = listOf(firstItemList, secondItemList, thirdItemList)
    val initialIndices = listOf(firstInitialIndex, secondInitialIndex, thirdInitialIndex)
    val itemListeners = listOf(onFirstItemChange, onSecondItemChange, onThirdItemChange)

    Box(
        modifier = modifier
            .background(color = YdsTheme.colors.dimThickBright)
            .padding(vertical = 16.dp)
            .height(totalItemHeightDp * 7),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            itemLists
                .forEachIndexed { i, itemList ->
                    if (itemList != null) {
                        WheelPicker(
                            itemList = itemList,
                            initialIndex = initialIndices[i],
                            totalItemHeightDp = totalItemHeightDp,
                            onItemChange = itemListeners[i]
                        )
                    }
                }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .height(totalItemHeightDp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(
                direction = Direction.Horizontal,
                thickness = Thickness.Thin
            )
            Divider(
                direction = Direction.Horizontal,
                thickness = Thickness.Thin
            )
        }
    }
}


@Composable
private fun WheelPicker(
    itemList: List<String>,
    initialIndex: Int,
    totalItemHeightDp: Dp,
    onItemChange: ((Int) -> Unit)?
) {
    val lazyListState = rememberLazyListState(initialIndex)
    val currentOnItemChange by rememberUpdatedState(onItemChange)

    // TODO: warning 뜨는거 고치기
    UpdateEffect(lazyListState.firstVisibleItemIndex) {
        currentOnItemChange?.invoke(lazyListState.firstVisibleItemIndex)
    }

    LazyColumn(
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(3) {
            PickerItem(totalItemHeightDp)
        }
        itemsIndexed(itemList) { i, text ->
            PickerItem(
                itemHeight = totalItemHeightDp,
                text = text,
                showed = (i == lazyListState.firstVisibleItemIndex)
            )
        }
        items(3) {
            PickerItem(totalItemHeightDp)
        }
    }

    val itemHeightPx = with(LocalDensity.current) { totalItemHeightDp.toPx() }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        val currentIndex = lazyListState.firstVisibleItemIndex

        if (lazyListState.firstVisibleItemScrollOffset > (itemHeightPx / 2)) {
            lazyListState.animateScrollToItem(currentIndex + 1)
        } else {
            lazyListState.animateScrollToItem(currentIndex)
        }
    }
}

@Composable
private fun PickerItem(
    itemHeight: Dp,
    text: String = "",
    showed: Boolean = false
) {
    Box(
        modifier = Modifier
            .height(itemHeight)
            .padding(
                horizontal = 20.dp,
                vertical = 4.dp
            )
    ) {
        YdsText(
            text = text,
            style = YdsTheme.typography.body1,
            color = if (showed) {
                YdsTheme.colors.textPrimary
            } else {
                YdsTheme.colors.textDisabled
            }
        )
    }
}

/**
 * The same as [LaunchedEffect] but skips the first invocation
 */
@Composable
fun UpdateEffect(key: Any, block: suspend CoroutineScope.() -> Unit) {
    var isTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(key) {
        if (isTriggered) {
            block()
        } else {
            isTriggered = true
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PickerPreview() {
    val items1 = listOf("오전", "오후")
    val items2 = (1..100).map { "$it" }.toList()

    YdsTheme {
        Picker(
            firstItemList = items1,
            secondItemList = items2,
            onFirstItemChange = null
        )
    }
}