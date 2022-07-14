package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.component.DoubleTitleTopBar
import com.yourssu.design.system.component.SearchTopBar
import com.yourssu.design.system.component.SingleTitleTopBar
import com.yourssu.design.system.component.TopBar

fun Context.topBar(block: TopBar.() -> Unit) = TopBar(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.topBar(block: TopBar.() -> Unit) = TopBar(this.context).run {
    block.invoke(this)
    this@topBar.addView(this)
    this
}

fun ComponentGroup.topBar(block: TopBar.() -> Unit) = TopBar(this.componentContext).run {
    block.invoke(this)
    this@topBar.addComponent(this)
    this
}

fun Context.singleTitleTopBar(block: SingleTitleTopBar.() -> Unit) = SingleTitleTopBar(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.singleTitleTopBar(block: SingleTitleTopBar.() -> Unit) = SingleTitleTopBar(this.context).run {
    block.invoke(this)
    this@singleTitleTopBar.addView(this)
    this
}

fun ComponentGroup.singleTitleTopBar(block: SingleTitleTopBar.() -> Unit) = SingleTitleTopBar(this.componentContext).run {
    block.invoke(this)
    this@singleTitleTopBar.addComponent(this)
    this
}

fun Context.doubleTitleTopBar(block: DoubleTitleTopBar.() -> Unit) = DoubleTitleTopBar(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.doubleTitleTopBar(block: DoubleTitleTopBar.() -> Unit) = DoubleTitleTopBar(this.context).run {
    block.invoke(this)
    this@doubleTitleTopBar.addView(this)
    this
}

fun ComponentGroup.doubleTitleTopBar(block: DoubleTitleTopBar.() -> Unit) = DoubleTitleTopBar(this.componentContext).run {
    block.invoke(this)
    this@doubleTitleTopBar.addComponent(this)
    this
}

fun Context.searchTopBar(block: SearchTopBar.() -> Unit) = SearchTopBar(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.searchTopBar(block: SearchTopBar.() -> Unit) = SearchTopBar(this.context).run {
    block.invoke(this)
    this@searchTopBar.addView(this)
    this
}

fun ComponentGroup.searchTopBar(block: SearchTopBar.() -> Unit) = SearchTopBar(this.componentContext).run {
    block.invoke(this)
    this@searchTopBar.addComponent(this)
    this
}