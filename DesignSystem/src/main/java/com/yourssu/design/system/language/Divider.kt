package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.Divider

fun Context.divider(block: Divider.() -> Unit) = Divider(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.divider(block: Divider.() -> Unit) = Divider(this.context).run {
    block.invoke(this)
    this@divider.addView(this)
    this
}

fun ComponentGroup.divider(block: Divider.() -> Unit) = Divider(this.componentContext).run {
    block.invoke(this)
    this@divider.addComponent(this)
    this
}