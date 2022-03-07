package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class TopBarViewModel(application: Application) : BaseViewModel(application) {
    val titleLiveData = MutableLiveData("Title")

    val startIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_arrow_left_line)
    val startIconText: MutableLiveData<String> = MutableLiveData("ic_arrow_left_line")
    val startIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val startText: MutableLiveData<String> = MutableLiveData("닫기")

    val endFirstIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_search_line)
    val endFirstIconText: MutableLiveData<String> = MutableLiveData("ic_search_line")
    val endFirstIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val endFirstText: MutableLiveData<String> = MutableLiveData("알림")

    val endSecondIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_bell_line)
    val endSecondIconText: MutableLiveData<String> = MutableLiveData("ic_bell_line")
    val endSecondIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val endSecondText: MutableLiveData<String> = MutableLiveData("검색")

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

    val onEndFirstTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                endFirstText.value = it
            }
        }
    }

    val onEndSecondTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                endSecondText.value = it
            }
        }
    }

    val startIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            startIconVisibility.value = boolean
        }
    }

    val endFirstIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endFirstIconVisibility.value = boolean
        }
    }

    val endSecondIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endSecondIconVisibility.value = boolean
        }
    }
}