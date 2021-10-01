package com.yourssu.storybook.atom

import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.BoxButton
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class BoxButtonViewModel: BaseViewModel() {
    val textString = MutableLiveData<String>("boxButton")
    val rounding: MutableLiveData<Int> = MutableLiveData(4)
    val roundingText: MutableLiveData<String> = MutableLiveData("4")
    val size: MutableLiveData<Int> = MutableLiveData(BoxButton.ExtraLarge)
    val sizeText: MutableLiveData<String> = MutableLiveData("ExtraLarge")
    val type: MutableLiveData<Int> = MutableLiveData(BoxButton.FILLED)
    val typeText: MutableLiveData<String> = MutableLiveData("FILLED")
    val isWarned: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDisable: MutableLiveData<Boolean> = MutableLiveData(false)

    val warnSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isWarned.value = boolean
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