package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class ListViewModel(application: Application) : BaseViewModel(application) {
    val subHeader: MutableLiveData<String> = MutableLiveData("subHeader")
    val listItemText: MutableLiveData<String> = MutableLiveData("ListItem")
    val listItemLeftIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val listItemLeftIconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val listItemLeftIconVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val listItemRightIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_adbadge_filled)
    val listItemRightIconText: MutableLiveData<String> = MutableLiveData("ic_adbadge_filled")
    val listItemRightIconVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val listToggleItemText: MutableLiveData<String> = MutableLiveData("ListToggleItem")
    val onSubHeaderTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            subHeader.value = s.toString()
        }
    }
    val isToggleSelected: MutableLiveData<Boolean> = MutableLiveData(false)

    val toggleSelectedListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isToggleSelected.value = boolean
        }
    }
    val onListItemTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listItemText.value = s.toString()
        }
    }
    val onListToggleItemTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listToggleItemText.value = s.toString()
        }
    }

    val leftIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            listItemLeftIconVisibility.value = boolean
        }
    }

    val rightIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            listItemRightIconVisibility.value = boolean
        }
    }
}