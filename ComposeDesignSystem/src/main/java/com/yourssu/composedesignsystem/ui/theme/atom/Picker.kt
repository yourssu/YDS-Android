package com.yourssu.composedesignsystem.ui.theme.atom

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme

class PickerState(
    initialIndex: Int = 0,
    val itemList: List<String>
) {
    internal val lazyListState = LazyListState(initialIndex)

    val currentIndex: Int
        get() = lazyListState.firstVisibleItemIndex

    val currentItemOffset: Int
        get() = lazyListState.firstVisibleItemScrollOffset

    companion object {
        fun saver(
            itemList: List<String>
        ): Saver<PickerState, *> = Saver(
            save = { it.currentIndex },
            restore = { PickerState(it, itemList) }
        )
    }
}

@Composable
fun rememberPickerState(
    initialIndex: Int = 0,
    itemList: List<String>
): PickerState = rememberSaveable(
    saver = PickerState.saver(itemList)
) {
    PickerState(initialIndex, itemList)
}

@Composable
fun Picker(
    state: PickerState,
    modifier: Modifier = Modifier,
    onSelectedItemChange: (currentIndex: Int) -> Unit = {}
) {
    // Height 제대로 측정 안 됨 (예상보다 짧게 됨)
    val itemHeightDp = with(LocalDensity.current) {
        YdsTheme.typography.body1.fontSize.toDp() + 4.dp * 2 // 위 아래 패딩 4dp
    }

    Box(
        modifier = Modifier
            .then(modifier)
            .background(color = YdsTheme.colors.dimThickBright)
            .wrapContentWidth()
            .height(itemHeightDp * 7)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = state.lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(3) {
                PickerItem(itemHeight = itemHeightDp)
            }
            itemsIndexed(state.itemList) { i, text ->
                PickerItem(
                    text = text,
                    itemHeight = itemHeightDp,
                    showed = (i == state.currentIndex)
                )
            }
            items(3) {
                PickerItem(itemHeight = itemHeightDp)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .height(itemHeightDp),
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

    val itemHeightPx = with(LocalDensity.current) { itemHeightDp.toPx() }

    LaunchedEffect(state.lazyListState.isScrollInProgress) {
        if (state.currentItemOffset > (itemHeightPx / 2)) {
            state.lazyListState.animateScrollToItem(
                state.currentIndex + 1
            )
        } else {
            state.lazyListState.animateScrollToItem(
                state.currentIndex
            )
        }
    }
}

@Composable
private fun PickerItem(
    text: String = "",
    itemHeight: Dp,
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
        Text(
            text = text,
            style = if (showed) {
                YdsTheme.typography.body1
            } else {
                YdsTheme.typography.body2
            },
            color = if (showed) {
                YdsTheme.colors.textPrimary
            } else {
                YdsTheme.colors.textDisabled
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PickerPreview() {
    val items = (1..100).map { it.toString() }.toList()
    val state = rememberPickerState(itemList = items)

    YdsTheme {
        Picker(state = state)
    }
}