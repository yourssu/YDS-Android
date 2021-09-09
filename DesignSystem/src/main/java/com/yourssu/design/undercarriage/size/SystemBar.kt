package com.yourssu.design.undercarriage.size

import android.content.Context

fun Context.getNavigationBarHeight(): Int {
    var bottomBarHeight = 0
    val resourceIdBottom: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceIdBottom > 0) bottomBarHeight = resources.getDimensionPixelSize(resourceIdBottom)
    return bottomBarHeight
}

fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) statusBarHeight = resources.getDimensionPixelSize(resourceId)
    return statusBarHeight
}