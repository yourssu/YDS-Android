package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.IconView

fun Context.iconView(block: IconView.() -> Unit) = IconView(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.iconView(block: IconView.() -> Unit) = IconView(this.context).run {
    block.invoke(this)
    this@iconView.addView(this)
    this
}

fun ComponentGroup.iconView(block: IconView.() -> Unit) = IconView(this.componentContext).run {
    block.invoke(this)
    this@iconView.addComponent(this)
    this
}