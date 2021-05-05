package com.yourssu.yds_android.util

import android.content.res.ColorStateList
import android.text.TextUtils
import android.view.View
import android.widget.TextView

fun TextView.safeSetTextColor(color: ColorStateList?) {
    if (color != null) {
        this.setTextColor(color)
    }
}

fun TextView.safeSetHintTextColor(color: ColorStateList?) {
    if (color != null) {
        this.setHintTextColor(color)
    }
}

fun TextView.setTextAndHideWhileEmpty(text: CharSequence?) {
    if (TextUtils.isEmpty(text)) {
        this.visibility = View.GONE
    } else {
        this.text = text
        this.visibility = View.VISIBLE
    }
}