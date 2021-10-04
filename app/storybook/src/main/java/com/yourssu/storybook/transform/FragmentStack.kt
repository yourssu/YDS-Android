package com.yourssu.storybook.transform

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FragmentStack constructor(private var fragmentManager: FragmentManager, @IdRes private var containerResId: Int, private val tag: String) {

    private var defaultAnimType: FragmentAnimType = FragmentAnimType.SLIDE

    fun setAnimType(animType: FragmentAnimType) {
        this.defaultAnimType = animType
    }

    fun setFirstStack(fragment: Fragment) {
        fragmentManager.beginTransaction().run {
            replace(containerResId, fragment, tag)
        }.commitAllowingStateLoss()
    }

    fun addStack(fragment: Fragment, animType: FragmentAnimType? = null) {
        fragmentManager.beginTransaction().run {
            setAnimation(this, animType ?: defaultAnimType)
            replace(containerResId, fragment, tag)
            addToBackStack(tag)
        }.commitAllowingStateLoss()
    }

    private fun setAnimation(transaction: FragmentTransaction, animType: FragmentAnimType) {
        transaction.setCustomAnimations(animType.startInAnim, animType.startOutAnim, animType.endInAnim, animType.endOutAnim)
    }

    fun popStack() {
        fragmentManager.popBackStack()
    }

    fun clearBackStack() {
        val backStackEntry = fragmentManager.backStackEntryCount

        repeat(backStackEntry) {
            fragmentManager.popBackStackImmediate()
        }
    }

    fun getTopFragment(): Fragment? = getFragmentsByTag().firstOrNull()

    fun backStackSize(): Int = fragmentManager.backStackEntryCount

    fun canBack(): Boolean = backStackSize() > 0

    // tag 가 동일한 fragment list
    private fun getFragmentsByTag(): List<Fragment> = fragmentManager.fragments
            .filter {
                it.tag == this.tag
            }
}
