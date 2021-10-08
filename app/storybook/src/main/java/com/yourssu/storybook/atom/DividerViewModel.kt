package com.yourssu.storybook.atom

import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Divider
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.ItemColor
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class DividerViewModel: BaseViewModel() {
    val direction: MutableLiveData<Int> = MutableLiveData(Divider.HORIZONTAL)
    val directionText: MutableLiveData<String> = MutableLiveData("HORIZONTAL")
    val thickness: MutableLiveData<Int> = MutableLiveData(Divider.THIN)
    val thicknessText: MutableLiveData<String> = MutableLiveData("THIN")
}