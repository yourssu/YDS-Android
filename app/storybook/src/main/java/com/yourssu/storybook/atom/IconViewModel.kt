package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.IconView
import com.yourssu.design.system.foundation.Icon
import com.yourssu.storybook.BaseViewModel

class IconViewModel(application: Application): BaseViewModel(application) {
    val icon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val iconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val size: MutableLiveData<Int> = MutableLiveData(IconView.ExtraSmall)
    val sizeText: MutableLiveData<String> = MutableLiveData("ExtraSmall")
}