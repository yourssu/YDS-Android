package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class SingleTitleTopBarViewModel(application: Application) : BaseViewModel(application) {
    val title = MutableLiveData("Title")

    val firstText = MutableLiveData("닫기")

    val firstIconText = MutableLiveData("ic_arrow_left_line")
    val firstIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_arrow_left_line)
    val firstIconVisibility = MutableLiveData(true)

    val secondText = MutableLiveData("알림")

    val secondIconText = MutableLiveData("ic_arrow_left_line")
    val secondIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_arrow_left_line)
    val secondIconVisibility = MutableLiveData(true)

    val thirdText = MutableLiveData("검색")

    val thirdIconText = MutableLiveData("ic_arrow_left_line")
    val thirdIcon: MutableLiveData<Int> = MutableLiveData(Icon.ic_arrow_left_line)
    val thirdIconVisibility = MutableLiveData(true)


    val onTitleTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { title.value = it }
        }
    }

    val firstTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                firstText.value = it
            }
        }
    }

    val secondTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                secondText.value = it
            }
        }
    }

    val thirdTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                thirdText.value = it
            }
        }
    }

    val firstIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            firstIconVisibility.value = boolean
        }
    }

    val secondIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            secondIconVisibility.value = boolean
        }
    }

    val thirdIconSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            thirdIconVisibility.value = boolean
        }
    }
}