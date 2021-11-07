package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.yourssu.design.R
import com.yourssu.design.system.atom.Text

fun Context.text(block: Text.() -> Unit) = Text(this).run {
    textColor(R.color.textPrimary)
    block.invoke(this)
    this
}

fun ViewGroup.text(block: Text.() -> Unit) = Text(this.context).run {
    textColor(R.color.textPrimary)
    block.invoke(this)
    this@text.addView(this)
    this
}

fun ComponentGroup.text(block: Text.() -> Unit) = Text(this.componentContext).run {
    textColor(R.color.textPrimary)
    block.invoke(this)
    this@text.addComponent(this)
    this
}

fun Text.textColor(@ColorRes color: Int) {
    setTextColor(this.context.getColor(color))
}