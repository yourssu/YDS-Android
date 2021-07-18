package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import android.widget.ScrollView

fun Context.scrollView(block: ScrollView.() -> Unit) = ScrollView(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.scrollView(block: ScrollView.() -> Unit) = ScrollView(this.context).run {
    block.invoke(this)
    this@scrollView.addView(this)
    this
}

fun ComponentGroup.scrollView(block: ScrollView.() -> Unit) = ScrollView(this.componentContext).run {
    block.invoke(this)
    this@scrollView.addComponent(this)
    this
}