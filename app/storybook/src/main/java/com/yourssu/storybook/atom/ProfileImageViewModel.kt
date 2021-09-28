package com.yourssu.storybook.atom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yourssu.design.system.atom.ProfileImageView
import com.yourssu.storybook.R

class ProfileImageViewModel: ViewModel() {
    val image: MutableLiveData<Int> = MutableLiveData(R.drawable.bunny)
    val size: MutableLiveData<Int> = MutableLiveData(ProfileImageView.Small)

    fun imageButtonClick(value: Int) {
        when (value) {
            0 -> R.drawable.bunny
            1 -> R.drawable.hox
            2 -> R.drawable.oldeng
            3 -> R.drawable.wind
            else -> 0
        }.let {
            image.value = it
        }
    }

    fun sizeButtonClick(value: Int) {
        size.value = value
    }
}