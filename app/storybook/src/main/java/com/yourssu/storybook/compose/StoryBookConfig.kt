package com.yourssu.storybook.compose

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.yourssu.design.system.compose.foundation.YdsTextStyle
import com.yourssu.design.system.compose.rule.ItemColor
import com.yourssu.design.system.compose.rule.YdsRounding

@Stable
class StoryBookConfig<SIZE, TYPE>(
    text: String = "",
    typo: YdsTextStyle? = null,
    rounding: YdsRounding? = null,
    size: SIZE? = null,
    type: TYPE? = null,
    isPointed: Boolean = false,
    isWarned: Boolean = false,
    isDisabled: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    itemColor: ItemColor? = null,
) {
    var text by mutableStateOf(text)
    var typo by mutableStateOf(typo)
    var rounding by mutableStateOf(rounding)
    var size by mutableStateOf(size)
    var type by mutableStateOf(type)
    var isPointed by mutableStateOf(isPointed)
    var isWarned by mutableStateOf(isWarned)
    var isDisabled by mutableStateOf(isDisabled)
    var leftIcon by mutableStateOf(leftIcon)
    var rightIcon by mutableStateOf(rightIcon)
    var itemColor by mutableStateOf(itemColor)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is StoryBookConfig<*, *>) return false

        if (text != other.text) return false
        if (typo != other.typo) return false
        if (rounding != other.rounding) return false
        if (size != other.size) return false
        if (typo != other.type) return false
        if (isPointed != other.isPointed) return false
        if (isWarned != other.isWarned) return false
        if (isDisabled != other.isDisabled) return false
        if (leftIcon != other.leftIcon) return false
        if (rightIcon != other.rightIcon) return false
        if (itemColor != other.itemColor) return false
        return true
    }
}
