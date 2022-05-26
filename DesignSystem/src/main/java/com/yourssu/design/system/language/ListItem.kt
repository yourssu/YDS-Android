package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.ListItem

fun Context.listItem(block: ListItem.() -> Unit) = ListItem(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.listItem(block: ListItem.() -> Unit) = ListItem(this.context).run {
    block.invoke(this)
    this@listItem.addView(this)
    this
}

fun ComponentGroup.listItem(block: ListItem.() -> Unit) = ListItem(this.componentContext).run {
    block.invoke(this)
    this@listItem.addComponent(this)
    this
}