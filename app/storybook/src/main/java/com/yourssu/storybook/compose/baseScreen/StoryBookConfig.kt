package com.yourssu.storybook.compose.baseScreen

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import com.yourssu.design.system.compose.foundation.YdsTextStyle
import com.yourssu.design.system.compose.rule.ItemColor

/**
 * [StoryBookConfig]에서 size, type 열거형 타입 추가된 클래스
 *
 * @param SIZE 사이즈 열거형
 * (ex: BoxButtonSize, PlainButtonSize, ProfileImageViewSize 등)
 * @param TYPE 버튼 타입 열거형
 * (ex: BoxButtonType 등)
 */
@Stable
class StoryBookConfig<SIZE, TYPE>(
    text: String = "",
    size: SIZE? = null,
    type: TYPE? = null,
    typo: YdsTextStyle = YdsTextStyle.Default,
    rounding: Dp = Dp.Unspecified,
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
    var isPointed by mutableStateOf(isPointed)
    var isWarned by mutableStateOf(isWarned)
    var isDisabled by mutableStateOf(isDisabled)
    var leftIcon by mutableStateOf(leftIcon)
    var rightIcon by mutableStateOf(rightIcon)
    var itemColor by mutableStateOf(itemColor)
    var size by mutableStateOf(size)
    var type by mutableStateOf(type)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoryBookConfig<*, *>

        if (text != other.text) return false
        if (typo != other.typo) return false
        if (rounding != other.rounding) return false
        if (isPointed != other.isPointed) return false
        if (isWarned != other.isWarned) return false
        if (isDisabled != other.isDisabled) return false
        if (leftIcon != other.leftIcon) return false
        if (rightIcon != other.rightIcon) return false
        if (itemColor != other.itemColor) return false
        if (size != other.size) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + typo.hashCode()
        result = 31 * result + rounding.hashCode()
        result = 31 * result + isPointed.hashCode()
        result = 31 * result + isWarned.hashCode()
        result = 31 * result + isDisabled.hashCode()
        result = 31 * result + (leftIcon ?: 0)
        result = 31 * result + (rightIcon ?: 0)
        result = 31 * result + (itemColor?.hashCode() ?: 0)
        result = 31 * result + (size?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        return result
    }
}
