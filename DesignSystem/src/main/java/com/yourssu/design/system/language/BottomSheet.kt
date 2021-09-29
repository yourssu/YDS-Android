package com.yourssu.design.system.language

import android.content.Context
import com.yourssu.design.system.atom.BottomSheet

fun Context.bottomSheet(block: BottomSheet.() -> Unit) = BottomSheet(this)
    .run {
        block.invoke(this)
        this
    }