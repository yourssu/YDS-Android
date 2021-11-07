package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class SimpleTextFieldViewModel(application: Application): BaseViewModel(application) {
    val fieldLabelTextString = MutableLiveData<String>("fieldLabelText")
    val helperLabelTextString = MutableLiveData<String>("helperLabelText")
    val placeholderTextString = MutableLiveData<String>("placeholderText")
    val isPositive: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNegative: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDisable: MutableLiveData<Boolean> = MutableLiveData(false)

    val positiveSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isPositive.value = boolean
        }
    }

    val negativeSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isNegative.value = boolean
        }
    }

    val disableSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isDisable.value = boolean
        }
    }

    val onFieldLabelTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { fieldLabelTextString.value = it }
        }
    }

    val onHelperLabelTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { helperLabelTextString.value = it }
        }
    }

    val onPlaceholderTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { placeholderTextString.value = it }
        }
    }
}