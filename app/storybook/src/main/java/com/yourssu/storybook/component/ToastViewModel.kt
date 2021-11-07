package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class ToastViewModel(application: Application): BaseViewModel(application) {
    val textString = MutableLiveData<String>("toast")
    val isLongTime: MutableLiveData<Boolean> = MutableLiveData(false)

    val onTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { textString.value = it }
        }
    }

    val longTimeSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isLongTime.value = boolean
        }
    }
}