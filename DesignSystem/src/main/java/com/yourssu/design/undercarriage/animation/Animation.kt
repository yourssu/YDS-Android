package com.yourssu.design.undercarriage.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.startAnim(@AnimRes animation: Int, listener: Animation.AnimationListener? = null) =
    this.startAnimation(AnimationUtils.loadAnimation(this.context, animation).apply {
        setAnimationListener(listener)
    })

fun endListener(block: () -> Unit) = object: Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {
        // do nothing
    }
    override fun onAnimationEnd(animation: Animation?) {
        block.invoke()
    }
    override fun onAnimationRepeat(animation: Animation?) {
        // do nothing
    }
}

fun startListener(block: () -> Unit) = object: Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {
        block.invoke()
    }
    override fun onAnimationEnd(animation: Animation?) {
        // do nothing
    }
    override fun onAnimationRepeat(animation: Animation?) {
        // do nothing
    }
}