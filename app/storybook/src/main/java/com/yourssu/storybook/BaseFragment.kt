package com.yourssu.storybook

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orientation = context?.resources?.configuration?.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onLandscape()
        } else {
            onPortrait()
        }
    }

    open fun onPortrait() {

    }

    open fun onLandscape() {

    }
}