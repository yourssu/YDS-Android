package com.yourssu.design.system.language

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setContentView(block: () -> View) {
    this.setContentView(block.invoke())
}

const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT

fun View.setLayout(
    width: Int? = null,
    height: Int? = null,
    leftMarginPx: Int? = null,
    rightMarginPx: Int? = null,
    topMarginPx: Int? = null,
    bottomMarginPx: Int? = null,
    leftPaddingPx: Int? = null,
    rightPaddingPx: Int? = null,
    topPaddingPx: Int? = null,
    bottomPaddingPx: Int? = null,
) {
    this.layoutParams = ViewGroup.MarginLayoutParams(
        width ?: WRAP_CONTENT,
        height ?: WRAP_CONTENT)
        .apply {
            leftMarginPx?.let { this.leftMargin = it }
            rightMarginPx?.let { this.rightMargin = it }
            topMarginPx?.let { this.topMargin = it }
            bottomMarginPx?.let { this.bottomMargin = it }
        }
    this.setPadding(leftPaddingPx ?: 0, topPaddingPx ?: 0, rightPaddingPx ?: 0, bottomPaddingPx ?: 0)
}

fun View.backgroundColor(@ColorRes color: Int) {
    this.setBackgroundColor(this.context.getColor(color))
}