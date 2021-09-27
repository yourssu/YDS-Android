package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.PlainButton

fun Context.plainButton(block: PlainButton.() -> Unit) = PlainButton(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.plainButton(block: PlainButton.() -> Unit) = PlainButton(this.context).run {
    block.invoke(this)
    this@plainButton.addView(this)
    this
}

fun ComponentGroup.plainButton(block: PlainButton.() -> Unit) = PlainButton(this.componentContext).run {
    block.invoke(this)
    this@plainButton.addComponent(this)
    this
}