package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.Badge

fun Context.badge(block: Badge.() -> Unit) = Badge(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.badge(block: Badge.() -> Unit) = Badge(this.context).run {
    block.invoke(this)
    this@badge.addView(this)
    this
}

fun ComponentGroup.badge(block: Badge.() -> Unit) = Badge(this.componentContext).run {
    block.invoke(this)
    this@badge.addComponent(this)
    this
}