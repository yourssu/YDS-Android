package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.Checkbox

fun Context.checkbox(block: Checkbox.() -> Unit) = Checkbox(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.checkbox(block: Checkbox.() -> Unit) = Checkbox(this.context).run {
    block.invoke(this)
    this@checkbox.addView(this)
    this
}

fun ComponentGroup.checkbox(block: Checkbox.() -> Unit) =
    Checkbox(this.componentContext).run {
        block.invoke(this)
        this@checkbox.addComponent(this)
        this
    }