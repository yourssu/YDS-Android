package com.yourssu.design.system.language

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.yourssu.design.system.atom.BottomSheet

fun Context.bottomSheet(block: BottomSheet.() -> Unit) = BottomSheet(this)
    .run {
        block.invoke(this)
        this
    }.show()

fun Activity.bottomSheet(block: BottomSheet.() -> Unit) = BottomSheet(this)
    .run {
        block.invoke(this)
        this
    }.show()

fun Fragment.bottomSheet(block: BottomSheet.() -> Unit) = this.activity?.let {
    BottomSheet(it)
        .run {
            block.invoke(this)
            this
        }
}?.show()