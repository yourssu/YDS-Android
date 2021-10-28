package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.ProfileImageView
import com.yourssu.storybook.BaseViewModel
import com.yourssu.storybook.R

class ProfileImageViewModel(application: Application): BaseViewModel(application) {
    val image: MutableLiveData<Int> = MutableLiveData(R.drawable.bunny)
    val imageText: MutableLiveData<String> = MutableLiveData("bunny")
    val size: MutableLiveData<Int> = MutableLiveData(ProfileImageView.Small)
    val sizeText: MutableLiveData<String> = MutableLiveData("ExtraSmall")

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