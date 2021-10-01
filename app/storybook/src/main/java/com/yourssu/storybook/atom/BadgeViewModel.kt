package com.yourssu.storybook.atom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.ItemColor
import com.yourssu.design.undercarriage.base.TextField

class BadgeViewModel: ViewModel() {
    val textString = MutableLiveData<String>("badge")

    val color: MutableLiveData<ItemColor> = MutableLiveData(ItemColor.Mono)
    val colorText: MutableLiveData<String> = MutableLiveData("Mono")
    val icon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val iconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")

    val onTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { textString.value = it }
        }
    }
}