package com.yourssu.storybook.compose.baseScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.yourssu.design.system.compose.atom.Picker
import com.yourssu.design.system.compose.foundation.YdsTextStyle
import com.yourssu.storybook.compose.icons
import com.yourssu.storybook.compose.typographies

enum class PickerType {
    Typo, LeftIcon, RightIcon,
}

/**
 * [StoryBookScreen]의 BottomSheet 내용에 들어갈 picker
 */
@Composable
fun StoryBookPicker(
    config: StoryBookConfig,
    pickerType: PickerType,
) {
    val (pickerFirstItem, keys) = remember(pickerType) {
        val map = when (pickerType) {
            PickerType.Typo -> typographies
            PickerType.LeftIcon, PickerType.RightIcon -> icons
        }
        (map to map.keys.toList())
    }

    Picker(
        firstItemList = keys,
        onFirstItemChange = { idx ->
            config.typo = pickerFirstItem[keys[idx]] as YdsTextStyle
        },
    )
}