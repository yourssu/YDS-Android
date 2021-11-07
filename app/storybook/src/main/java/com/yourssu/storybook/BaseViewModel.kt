package com.yourssu.storybook

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

@SuppressLint("ResourceType")
abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    val componentBackgroundColor = MutableLiveData(application.getColor(R.drawable.gray500))

    fun changeBackground() {
        when (componentBackgroundColor.value) {
            getApplication<Application>().getColor(R.drawable.gray500) -> {
                componentBackgroundColor.value = getApplication<Application>().getColor(R.drawable.white000)
            }
            getApplication<Application>().getColor(R.drawable.white000) -> {
                componentBackgroundColor.value = getApplication<Application>().getColor(R.drawable.black000)
            }
            getApplication<Application>().getColor(R.drawable.black000) -> {
                componentBackgroundColor.value = getApplication<Application>().getColor(R.drawable.gray500)
            }
        }
    }
}