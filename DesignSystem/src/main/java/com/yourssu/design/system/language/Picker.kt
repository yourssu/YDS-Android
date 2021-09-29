package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.Picker

fun Context.picker(block: Picker.() -> Unit) = Picker(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.picker(block: Picker.() -> Unit) = Picker(this.context).run {
    block.invoke(this)
    this@picker.addView(this)
    this
}

fun ComponentGroup.picker(block: Picker.() -> Unit) = Picker(this.componentContext).run {
    block.invoke(this)
    this@picker.addComponent(this)
    this
}