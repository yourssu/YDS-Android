package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout

fun Context.verticalLayout(block: LinearLayout.() -> Unit) = LinearLayout(this).run {
    orientation = LinearLayout.VERTICAL
    block.invoke(this)
    this
}

fun ViewGroup.verticalLayout(block: LinearLayout.() -> Unit) = LinearLayout(this.context).run {
    orientation = LinearLayout.VERTICAL
    block.invoke(this)
    this@verticalLayout.addView(this)
    this
}

fun ComponentGroup.verticalLayout(block: LinearLayout.() -> Unit) = LinearLayout(this.componentContext).run {
    orientation = LinearLayout.VERTICAL
    block.invoke(this)
    this@verticalLayout.addComponent(this)
    this
}

fun Context.horizontalLayout(block: LinearLayout.() -> Unit) = LinearLayout(this).run {
    orientation = LinearLayout.HORIZONTAL
    block.invoke(this)
    this
}

fun ViewGroup.horizontalLayout(block: LinearLayout.() -> Unit) = LinearLayout(this.context).run {
    orientation = LinearLayout.HORIZONTAL
    block.invoke(this)
    this@horizontalLayout.addView(this)
    this
}

fun ComponentGroup.horizontalLayout(block: LinearLayout.() -> Unit) = LinearLayout(this.componentContext).run {
    orientation = LinearLayout.HORIZONTAL
    block.invoke(this)
    this@horizontalLayout.addComponent(this)
    this
}