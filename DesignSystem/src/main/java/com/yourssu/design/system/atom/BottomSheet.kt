package com.yourssu.design.system.atom

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutBottomSheetBinding
import com.yourssu.design.undercarriage.base.BaseFullDialog
import com.yourssu.design.system.language.ComponentGroup

class BottomSheet constructor(context: Context): BaseFullDialog(context), ComponentGroup {
    // 생성되기 전에 호출되는 경우 존재
    private val binding: LayoutBottomSheetBinding by lazy {
        LayoutBottomSheetBinding.inflate(layoutInflater, null, false)
    }

    override val componentContext: Context = context

    override fun addComponent(view: View) {
        binding.bottomSheetContent.addView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.dim.setOnClickListener {
            dismiss()
        }

        setContentView(binding.root)
    }

    override fun show() {
        super.show()
        binding.bottomSheetContent.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popup_in_motion_m))
        binding.dim.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_motion_m))
    }

    override fun dismiss() {
        binding.dim.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_motion_m))
        binding.bottomSheetContent.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popup_out_motion_m).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    super@BottomSheet.dismiss()
                }

            })
        })
    }
}