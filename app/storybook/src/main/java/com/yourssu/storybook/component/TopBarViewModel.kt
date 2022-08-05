package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class TopBarViewModel(application: Application) : BaseViewModel(application) {
    val titleLiveData = MutableLiveData("Title")

    val startButtonDisabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val startIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_arrow_left_line)
    val startIconText: MutableLiveData<String> = MutableLiveData("ic_arrow_left_line")
    val startIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val startText: MutableLiveData<String> = MutableLiveData("닫기")

    val endRightButtonDisabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val endRightIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_search_line)
    val endRightIconText: MutableLiveData<String> = MutableLiveData("ic_search_line")
    val endRightIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val endRightText: MutableLiveData<String> = MutableLiveData("알림")

    val endLeftButtonDisabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val endLeftIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_bell_line)
    val endLeftIconText: MutableLiveData<String> = MutableLiveData("ic_bell_line")
    val endLeftIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val endLeftText: MutableLiveData<String> = MutableLiveData("검색")

    val onTitleTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { titleLiveData.value = it }
        }
    }

    val onStartTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                startText.value = it
            }
        }
    }

    val onEndRightTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                endRightText.value = it
            }
        }
    }

    val onEndLeftTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                endLeftText.value = it
            }
        }
    }

    val startIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            startIconVisibility.value = boolean
        }
    }

    val endRightIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endRightIconVisibility.value = boolean
        }
    }

    val endLeftIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endLeftIconVisibility.value = boolean
        }
    }

    val startDisabledSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            startButtonDisabled.value = boolean
        }
    }

    val endRightDisabledSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endRightButtonDisabled.value = boolean
        }
    }

    val endLeftDisabledSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endLeftButtonDisabled.value = boolean
        }
    }
}