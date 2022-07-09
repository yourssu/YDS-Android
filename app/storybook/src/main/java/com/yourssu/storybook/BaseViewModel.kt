package com.yourssu.storybook

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

@SuppressLint("ResourceType")
abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    val componentBackgroundColor = MutableLiveData(application.getColor(R.drawable.gray500))
    val componentTextColor = MutableLiveData(application.getColor(R.drawable.black000))
    val whiteSelected = MutableLiveData(false)
    val blackSelected = MutableLiveData(false)
    val graySelected = MutableLiveData(true)

    val isLandscape = MutableLiveData(false)

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

    fun changeWhiteBackground() {
        componentBackgroundColor.value = getApplication<Application>().getColor(R.drawable.white000)
        componentTextColor.value = getApplication<Application>().getColor(R.drawable.black000)
        whiteSelected.value = true
        blackSelected.value = false
        graySelected.value = false
    }

    fun changeBlackBackground() {
        componentBackgroundColor.value = getApplication<Application>().getColor(R.drawable.black000)
        componentTextColor.value = getApplication<Application>().getColor(R.drawable.white000)
        whiteSelected.value = false
        blackSelected.value = true
        graySelected.value = false
    }

    fun changeGrayBackground() {
        componentBackgroundColor.value = getApplication<Application>().getColor(R.drawable.gray500)
        componentTextColor.value = getApplication<Application>().getColor(R.drawable.white000)
        whiteSelected.value = false
        blackSelected.value = false
        graySelected.value = true
    }
}