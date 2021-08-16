package com.yourssu.design.system.component

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.yourssu.design.R
import com.yourssu.design.undercarriage.animation.endListener
import com.yourssu.design.undercarriage.animation.startAnim
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.getNavigationBarHeight

@SuppressLint("ViewConstructor")
class Toast private constructor(
    private val parent: ViewGroup,
    message: String,
    @Duration private val length: Int,
    offset: Int,
    includeNavigationBar: Boolean = false
) : LinearLayout(parent.context) {

    init {
        View.inflate(context, R.layout.layout_toast, this)
        findViewById<TextView>(R.id.content).text = message

        val offsetSize = if (includeNavigationBar) {
            context.getNavigationBarHeight() + context.dpToIntPx(offset.toFloat())
        } else {
            context.dpToIntPx(offset.toFloat())
        }
        findViewById<View>(R.id.offset).layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, offsetSize)
    }

    private fun preSetting() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            gravity = Gravity.BOTTOM
        }


        parent.addView(this)
        this.id = generateViewId()

        when (parent) {
            is ConstraintLayout -> {
                val constraintSet = ConstraintSet()

                parent.children.forEach {
                    if (it.id == View.NO_ID) {
                        it.id = generateViewId()
                    }
                }

                constraintSet.clone(parent)

                constraintSet.connect(
                    this.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    0
                )

                constraintSet.applyTo(parent)
            }
            is LinearLayout -> {
                when (parent.orientation) {
                    HORIZONTAL -> {
                        val params =
                            LayoutParams(parent.width, LayoutParams.MATCH_PARENT)
                                .apply {
                                    gravity = Gravity.BOTTOM
                                }
                        params.leftMargin = -(parent.children.map { it.width }.sum())
                        layoutParams = params
                    }
                    VERTICAL -> {
                        val params =
                            LayoutParams(LayoutParams.MATCH_PARENT, parent.height)
                                .apply {
                                    gravity = Gravity.BOTTOM
                                }
                        params.topMargin = -(parent.children.map { it.height }.sum())
                        layoutParams = params
                    }
                }
            }
        }
    }

    private fun showAnimation() {
        startAnim(R.anim.fade_in_motion_m, endListener {
            startAnim(
                if (length == Long) R.anim.none_motion_toast_long else R.anim.none_motion_toast_short,
                endListener {
                    startAnim(R.anim.fade_out_motion_m, endListener {
                        parent.removeView(this)
                    })
                })
        })
    }

    fun show() {
        preSetting()
        showAnimation()
    }

    @IntDef(value = [Short, Long])
    annotation class Duration

    companion object {
        const val Short = 1500
        const val Long = 3000

        // 추후 특정 뷰에 붙이는 스펙이 나오면 private 해제
        private fun ViewGroup.toast(
            message: String,
            @Toast.Duration length: Int,
            offsetY: Int? = null
        ) {
            Toast(this, message, length, offsetY ?: 0).show()
        }

        fun Activity.toast(message: String, @Toast.Duration length: Int? = null) {
            Toast(this.window.decorView as ViewGroup, message, length ?: Short, 56, true).show()
        }

        fun Activity.shortToast(message: String) {
            toast(message, Short)
        }

        fun Activity.longToast(message: String) {
            toast(message, Long)
        }

        fun Fragment.toast(message: String, @Toast.Duration length: Int? = null) {
            activity?.toast(message, length)
        }

        fun Fragment.shortToast(message: String) {
            activity?.shortToast(message)
        }

        fun Fragment.longToast(message: String) {
            activity?.longToast(message)
        }
    }
}