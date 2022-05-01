package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class ListItemViewModel(application: Application) : BaseViewModel(application) {
    val textString = MutableLiveData("List Item")
    val leftIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val leftIconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val leftIconVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val rightIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val rightIconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val rightIconVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    val onTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { textString.value = it }
        }
    }

    val leftIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            leftIconVisibility.value = boolean
        }
    }

    val rightIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            rightIconVisibility.value = boolean
        }
    }
}