package com.yourssu.yds_android.util

import android.graphics.drawable.Drawable

fun Drawable?.resize(width: Int, height: Int = width) {
    if (this == null) {
        return
    }

    setBounds(
        0,
        0,
        if (width > 0) width else intrinsicWidth,
        if (height > 0) height else intrinsicHeight
    )
}