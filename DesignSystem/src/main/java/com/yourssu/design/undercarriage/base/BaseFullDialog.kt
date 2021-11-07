package com.yourssu.design.undercarriage.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import com.yourssu.design.R


abstract class BaseFullDialog: Dialog {
    constructor(context: Context) : super(context, R.style.Dialog_FullScreen)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullDialog()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setFullDialog(isAttachWindow = true)
    }

    @Suppress("DEPRECATION")
    private fun setFullDialog(isAttachWindow: Boolean = false) {
        if (isAttachWindow && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val insetController: WindowInsetsController? = window?.insetsController
            // insetController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            insetController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE
        } else if (!isAttachWindow && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}