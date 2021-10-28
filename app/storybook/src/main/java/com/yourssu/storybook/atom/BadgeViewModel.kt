package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.ItemColor
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class BadgeViewModel(application: Application): BaseViewModel(application) {
    val textString = MutableLiveData<String>("badge")
    val color: MutableLiveData<ItemColor> = MutableLiveData(ItemColor.Mono)
    val colorText: MutableLiveData<String> = MutableLiveData("Mono")
    val icon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val iconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val iconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val iconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            iconVisibility.value = boolean
        }
    }

    val onTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { textString.value = it }
        }
    }
}