package com.yourssu.yds_android.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

fun Context?.dpToPx(dp: Int): Int = this?.let {
    try {
        val floatValue = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            it.resources.displayMetrics
        )
        (if (floatValue > 0) floatValue + 0.5f else floatValue - 0.5f).toInt()
    } catch (e: Exception) {
        dp
    }
} ?: dp

fun Context?.dpToPxFloat(dp: Int): Float = this?.let {
    try {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            it.resources.displayMetrics
        )
    } catch (e: Exception) {
        dp.toFloat()
    }
} ?: dp.toFloat()

fun Context?.spToPx(sp: Int): Int = this?.let {
    try {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp.toFloat(),
            it.resources.displayMetrics
        ).toInt()
    } catch (e: Exception) {
        sp
    }
} ?: sp

@ColorInt
fun Context?.safeGetColor(@ColorRes colorRes: Int): Int {
    if (this == null || colorRes == 0) {
        return Color.TRANSPARENT
    }

    return try {
        ResourcesCompat.getColor(this.resources, colorRes, this.theme)
    } catch (e: Exception) {
        Color.TRANSPARENT
    }
}

fun Context?.safeGetColorStateList(@ColorRes colorRes: Int): ColorStateList? {
    if (this == null || colorRes == 0) {
        return null
    }

    return try {
        ResourcesCompat.getColorStateList(this.resources, colorRes, this.theme)
    } catch (e: Exception) {
        null
    }
}

fun Context?.safeGetDrawable(@DrawableRes drawableRes: Int): Drawable? {
    if (this == null || drawableRes == 0) {
        return null
    }

    return try {
        ResourcesCompat.getDrawable(this.resources, drawableRes, this.theme)
    } catch (e: Exception) {
        null
    }
}

fun Context?.safeGetDrawableWithSize(
    @DrawableRes drawableRes: Int,
    width: Int,
    height: Int
): Drawable? {
    if (this == null || drawableRes == 0) {
        return null
    }

    return try {
        ContextCompat.getDrawable(this, drawableRes)?.let { vectorDrawable ->
            val scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(scaledBitmap)
            canvas.drawFilter =
                PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
            vectorDrawable.setBounds(0, 0, width, height)
            vectorDrawable.draw(canvas)
            val resizedDrawable: Drawable = BitmapDrawable(resources, scaledBitmap)
            resizedDrawable
        }
    } catch (e: Exception) {
        null
    }
}

fun Context?.safeGetBitmap(@DrawableRes drawableRes: Int): Bitmap? {
    if (this == null || drawableRes == 0) {
        return null
    }

    val drawable = AppCompatResources.getDrawable(this, drawableRes)

    return if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else if (drawable is VectorDrawable || drawable is VectorDrawableCompat) {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    } else null
}