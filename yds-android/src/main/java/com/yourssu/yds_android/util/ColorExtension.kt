package com.yourssu.yds_android.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt

fun ColorStateList?.getColorForCurrentState(
    drawableState: IntArray,
    @ColorInt defaultColor: Int = Color.TRANSPARENT
): Int =
    this?.getColorForState(drawableState, defaultColor) ?: defaultColor

fun View?.getColorForCurrentState(colorStateList: ColorStateList?): Int =
    this?.run {
        colorStateList?.getColorForState(drawableState, Color.TRANSPARENT)
    } ?: Color.TRANSPARENT

internal fun createAlphaColor(@ColorInt color: Int, alpha: Float): Int {
    return color and 0x00FFFFFF or ((0xff * alpha).toInt() shl 24)
}