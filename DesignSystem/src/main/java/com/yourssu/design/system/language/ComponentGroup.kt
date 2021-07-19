package com.yourssu.design.system.language

import android.content.Context
import android.view.View

interface ComponentGroup {
    val componentContext: Context

    fun addComponent(view: View)
}