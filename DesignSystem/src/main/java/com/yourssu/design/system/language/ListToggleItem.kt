package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.ListToggleItem

fun Context.listToggleItem(block: ListToggleItem.() -> Unit) = ListToggleItem(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.listToggleItem(block: ListToggleItem.() -> Unit) = ListToggleItem(this.context).run {
    block.invoke(this)
    this@listToggleItem.addView(this)
    this
}

fun ComponentGroup.listToggleItem(block: ListToggleItem.() -> Unit) =
    ListToggleItem(this.componentContext).run {
        block.invoke(this)
        this@listToggleItem.addComponent(this)
        this
    }