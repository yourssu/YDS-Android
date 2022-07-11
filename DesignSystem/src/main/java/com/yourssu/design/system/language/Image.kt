package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.yourssu.design.system.atom.ProfileImageView

fun Context.imageView(block: AppCompatImageView.() -> Unit) = AppCompatImageView(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.imageView(block: AppCompatImageView.() -> Unit) = AppCompatImageView(this.context).run {
    block.invoke(this)
    this@imageView.addView(this)
    this
}

fun ComponentGroup.imageView(block: AppCompatImageView.() -> Unit) = AppCompatImageView(this.componentContext).run {
    block.invoke(this)
    this@imageView.addComponent(this)
    this
}

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