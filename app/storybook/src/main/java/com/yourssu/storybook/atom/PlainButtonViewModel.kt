package com.yourssu.storybook.atom

import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.BoxButton
import com.yourssu.design.system.atom.PlainButton
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class PlainButtonViewModel: BaseViewModel() {
    val textString = MutableLiveData<String>("plainButton")
    val size: MutableLiveData<Int> = MutableLiveData(PlainButton.SMALL)
    val sizeText: MutableLiveData<String> = MutableLiveData("SMALL")
    val isPoint: MutableLiveData<Boolean> = MutableLiveData(false)
    val isWarned: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDisable: MutableLiveData<Boolean> = MutableLiveData(false)
    val leftIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val leftIconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val leftIconVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val rightIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val rightIconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val rightIconVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    val pointSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isPoint.value = boolean
        }
    }

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