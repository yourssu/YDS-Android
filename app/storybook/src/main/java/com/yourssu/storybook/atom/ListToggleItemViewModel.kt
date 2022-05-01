package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class ListToggleItemViewModel(application: Application) : BaseViewModel(application) {
    val textString = MutableLiveData("ListToggleItem")
    val isToggleSelected: MutableLiveData<Boolean> = MutableLiveData(false)

    val toggleSelectedListener = object : Toggle.SelectedListener{
        override fun onSelected(boolean: Boolean) {
            isToggleSelected.value = boolean
        }
    }

    val onTextChangedListener = object : TextField.OnTextChanged{
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textString.value = s.toString()
        }
    }

}