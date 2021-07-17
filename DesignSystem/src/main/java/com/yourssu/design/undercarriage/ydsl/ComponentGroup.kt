package com.yourssu.design.undercarriage.ydsl

import android.content.Context
import android.view.View

interface ComponentGroup {
    val componentContext: Context

    fun addComponent(view: View)
}