package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class TopBarViewModel(application: Application) : BaseViewModel(application) {
    val titleLiveData = MutableLiveData("Title")

    val startIcon: MutableLiveData<Int?> = MutableLiveData(Icon.ic_arrow_left_line)
    val startIconText: MutableLiveData<String> = MutableLiveData("ic_arrow_left_line")
    val startIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val startText: MutableLiveData<String> = MutableLiveData("열기")
    val startTextVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    var tempStartText: String? = null

    val endFirstIcon: MutableLiveData<Int?> = MutableLiveData(Icon.ic_search_line)
    val endFirstIconText: MutableLiveData<String> = MutableLiveData("ic_search_line")
    val endFirstIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val endFirstText: MutableLiveData<String> = MutableLiveData("닫기")
    val endFirstTextVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    val endSecondIcon: MutableLiveData<Int?> = MutableLiveData(Icon.ic_bell_line)
    val endSecondIconText: MutableLiveData<String> = MutableLiveData("ic_bell_line")
    val endSecondIconVisibility: MutableLiveData<Boolean> = MutableLiveData(true)

    val endSecondText: MutableLiveData<String> = MutableLiveData("검색")
    val endSecondTextVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    val onTitleTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { titleLiveData.value = it }
        }
    }

    val onStartTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { startText.value = it }
        }
    }

    val onEndFirstTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { endFirstText.value = it }
        }
    }

    val onEndSecondTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { endSecondText.value = it }
        }
    }

    val startIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            startTextVisibility.value = !boolean
            startIconVisibility.value = boolean
            if (boolean) {
                startText.value = ""
            }
        }
    }

    val startTextSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            startIconVisibility.value = !boolean
            startTextVisibility.value = boolean
            if (boolean) {
                startIcon.value = null
            }
        }
    }

    val endFirstIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endFirstTextVisibility.value = !boolean
            endFirstIconVisibility.value = boolean
            if (boolean) {
                endFirstText.value = ""
            }
        }
    }

    val endFirstTextSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endFirstIconVisibility.value = !boolean
            endFirstTextVisibility.value = boolean
            if (boolean) {
                endFirstIcon.value = null
            }
        }
    }

    val endSecondIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endSecondTextVisibility.value = !boolean
            endSecondIconVisibility.value = boolean
            if (boolean) {
                endSecondText.value = ""
            }
        }
    }

    val endSecondTextSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            endSecondIconVisibility.value = !boolean
            endSecondTextVisibility.value = boolean
            if (boolean) {
                endSecondIcon.value = null
            }
        }
    }
}