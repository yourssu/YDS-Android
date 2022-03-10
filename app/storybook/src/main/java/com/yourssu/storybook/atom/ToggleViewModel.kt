package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Checkbox
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class ToggleViewModel(application: Application): BaseViewModel(application) {
    val textString = MutableLiveData<String>("Checkbox")
    val size: MutableLiveData<Int> = MutableLiveData(Checkbox.SMALL)
    val sizeText: MutableLiveData<String> = MutableLiveData("SMALL")
    val isSelect: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDisable: MutableLiveData<Boolean> = MutableLiveData(false)

    val selectedStateListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isSelect.value = boolean
        }
    }

    val selectSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isSelect.value = boolean
        }
    }

    val disableSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isDisable.value = boolean
        }
    }

    val onTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { textString.value = it }
        }
    }

}