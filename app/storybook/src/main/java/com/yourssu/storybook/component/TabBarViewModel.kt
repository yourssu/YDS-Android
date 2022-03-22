package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.storybook.BaseViewModel

class TabBarViewModel(application: Application) : BaseViewModel(application) {
    val tabMode: MutableLiveData<String> = MutableLiveData("scrollable")
}