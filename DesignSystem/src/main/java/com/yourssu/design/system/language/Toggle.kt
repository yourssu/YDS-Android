package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.Toggle

fun Context.toggle(block: Toggle.() -> Unit) = Toggle(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.toggle(block: Toggle.() -> Unit) = Toggle(this.context).run {
    block.invoke(this)
    this@toggle.addView(this)
    this
}

fun ComponentGroup.toggle(block: Toggle.() -> Unit) = Toggle(this.componentContext).run {
    block.invoke(this)
    this@toggle.addComponent(this)
    this
}