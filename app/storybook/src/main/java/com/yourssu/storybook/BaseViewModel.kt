package com.yourssu.storybook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    val componentBackgroundColor = MutableLiveData(R.color.borderThick)

    fun changeBackground() {
        if (componentBackgroundColor.value == R.color.borderThick) {
            componentBackgroundColor.value = R.color.dimThick
        } else {
            componentBackgroundColor.value = R.color.borderThick
        }
    }
}