package com.yourssu.design.compose.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.compose.YdsTheme

class PickerState(
    val itemList: List<String>,
    initialIndex: Int = 0
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
            restore = { PickerState(itemList, it) }
        )
    }
}

@Composable
fun rememberPickerStates(
    firstItemList: List<String>,
    secondItemList: List<String>? = null,
    thirdItemList: List<String>? = null,
    firstInitialIndex: Int = 0,
    secondInitialIndex: Int = 0,
    thirdInitialIndex: Int = 0
): List<PickerState?> {
    return listOf(
        rememberPickerState(
            itemList = firstItemList,
            initialIndex = firstInitialIndex
        ),
        rememberPickerState(
            itemList = secondItemList,
            initialIndex = secondInitialIndex
        ),
        rememberPickerState(
            itemList = thirdItemList,
            initialIndex = thirdInitialIndex
        )
    )
}

@Composable
private fun rememberPickerState(
    itemList: List<String>?,
    initialIndex: Int = 0
): PickerState? = itemList?.let {
    rememberSaveable(saver = PickerState.saver(itemList)) {
        PickerState(itemList, initialIndex)
    }
}

interface PickerItemChangeListener {
    fun onFirstItemChange(index: Int)
}

@Composable
fun Picker(
    stateList: List<PickerState?>,
    modifier: Modifier = Modifier,
    onFirstItemChange: (index: Int) -> Unit = {},
    onSecondItemChange: (index: Int) -> Unit = {},
    onThirdItemChange: (index: Int) -> Unit = {}
) {
    val itemHeightDp: Dp = with(LocalDensity.current) {
        YdsTheme.typography.body1.lineHeight.toDp()
    }
    val totalItemHeightDp = itemHeightDp + 4.dp * 2 // 위 아래 패딩 4dp

    val listenerList = listOf(onFirstItemChange, onSecondItemChange, onThirdItemChange)

    Box(
        modifier = Modifier
            .then(modifier)
            .background(color = YdsTheme.colors.dimThickBright)
            .padding(vertical = 16.dp)
            .height(totalItemHeightDp * 7),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            stateList.forEachIndexed { i, state ->
                state?.let {
                    WheelPicker(
                        state = it,
                        itemHeightDp = itemHeightDp,
                        totalItemHeightDp = totalItemHeightDp,
                        onItemChange = listenerList[i]
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
    state: PickerState,
    itemHeightDp: Dp,
    totalItemHeightDp: Dp,
    onItemChange: (Int) -> Unit
) {
    val currentOnItemChange by rememberUpdatedState(onItemChange)

    LaunchedEffect(state.currentIndex) {
        currentOnItemChange(state.currentIndex)
    }

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

    val itemHeightPx = with(LocalDensity.current) { totalItemHeightDp.toPx() }

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
    itemHeight: Dp,
    text: String = "",
    showed: Boolean = false
) {
    Box(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 4.dp
            )
            .height(itemHeight)
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
    val items1 = listOf("오전", "오후")
    val items2 = (1..100).map { it.toString() }.toList()
    val state = rememberPickerStates(
        firstItemList = items1,
        secondItemList = items2
    )
    
    var text by remember {
        mutableStateOf("")
    }

    YdsTheme {
        Column {
            Picker(
                stateList = state,
                onFirstItemChange = {
                    text = items1[it]
                }
            )
            Text(text = text)
        }
    }
}