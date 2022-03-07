package com.yourssu.design.undercarriage.size

import android.content.Context
import android.util.TypedValue
import androidx.annotation.DimenRes
import kotlin.math.roundToInt

infix fun Context.dpToPx(dp: Float): Float {
    val displayMetrics = this.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
}

infix fun Context.dpToIntPx(dp: Float): Int {
    val displayMetrics = this.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).roundToInt()
}

infix fun Context.getDimenFloat(@DimenRes dimenRes: Int): Float = this.resources.getDimension(dimenRes)

infix fun Context.getDimenInt(@DimenRes dimenRes: Int): Int = this.resources.getDimensionPixelSize(dimenRes)

