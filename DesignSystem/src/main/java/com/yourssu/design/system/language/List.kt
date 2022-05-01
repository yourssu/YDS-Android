package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.component.List

fun Context.list(block: List.() -> Unit) = List(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.list(block: List.() -> Unit) = List(this.context).run {
    block.invoke(this)
    this@list.addView(this)
    this
}

fun ComponentGroup.list(block: List.() -> Unit) = List(this.componentContext).run {
    block.invoke(this)
    this@list.addComponent(this)
    this
}