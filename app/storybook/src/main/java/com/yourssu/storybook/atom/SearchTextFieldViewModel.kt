package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class SearchTextFieldViewModel(application: Application): BaseViewModel(application) {
    val placeholderTextString = MutableLiveData<String>("placeholderText")
    val isDisable: MutableLiveData<Boolean> = MutableLiveData(false)

    val disableSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isDisable.value = boolean
        }
    }

    val onPlaceholderTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { placeholderTextString.value = it }
        }
    }
}