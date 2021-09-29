package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.CheckBox

fun Context.checkBox(block: CheckBox.() -> Unit) = CheckBox(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.checkBox(block: CheckBox.() -> Unit) = CheckBox(this.context).run {
    block.invoke(this)
    this@checkBox.addView(this)
    this
}

fun ComponentGroup.checkBox(block: CheckBox.() -> Unit) =
    CheckBox(this.componentContext).run {
        block.invoke(this)
        this@checkBox.addComponent(this)
        this
    }