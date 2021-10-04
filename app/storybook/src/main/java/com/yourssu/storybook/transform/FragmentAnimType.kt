package com.yourssu.storybook.transform

import androidx.annotation.AnimRes
import com.yourssu.storybook.R

/**
 * fragment 의 애니메이션 타입 정의
 */
enum class FragmentAnimType(@AnimRes val startInAnim: Int, @AnimRes val startOutAnim: Int, @AnimRes val endInAnim: Int, @AnimRes val endOutAnim: Int) {
    STAY(R.anim.fade_in_motion_m, R.anim.none_motion, R.anim.fade_in_motion_m, R.anim.none_motion),
    STAY_DIRECT(R.anim.none_motion, R.anim.none_motion, R.anim.none_motion, R.anim.none_motion),
    SLIDE(R.anim.slide_in_motion_m,
        R.anim.none_motion,
        R.anim.fade_in_motion_m,
        R.anim.slide_out_motion_m),
    POPUP(R.anim.popup_in_motion_m,
        R.anim.none_motion,
        R.anim.fade_in_motion_m,
        R.anim.popup_out_motion_m)
}