package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.ProfileImageView

fun Context.profileImageView(block: ProfileImageView.() -> Unit) = ProfileImageView(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.profileImageView(block: ProfileImageView.() -> Unit) = ProfileImageView(this.context).run {
    block.invoke(this)
    this@profileImageView.addView(this)
    this
}

fun ComponentGroup.profileImageView(block: ProfileImageView.() -> Unit) = ProfileImageView(this.componentContext).run {
    block.invoke(this)
    this@profileImageView.addComponent(this)
    this
}