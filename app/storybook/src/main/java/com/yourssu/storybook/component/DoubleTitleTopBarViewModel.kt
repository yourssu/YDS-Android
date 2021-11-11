package com.yourssu.storybook.component

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class DoubleTitleTopBarViewModel(application: Application) : BaseViewModel(application) {
    val title = MutableLiveData("Title")
    val subTitle = MutableLiveData("subTitle")

    val firstIconText = MutableLiveData("ic_arrow_left_line")
    val firstIcon: MutableLiveData<Int?> = MutableLiveData(Icon.ic_arrow_left_line)
    val firstText = MutableLiveData("")

    val secondIconText = MutableLiveData("ic_arrow_left_line")
    val secondIcon: MutableLiveData<Int?> = MutableLiveData(Icon.ic_arrow_left_line)
    val secondText = MutableLiveData("")

    val thirdIconText = MutableLiveData("ic_arrow_left_line")
    val thirdIcon: MutableLiveData<Int?> = MutableLiveData(Icon.ic_arrow_left_line)
    val thirdText = MutableLiveData("")

    val onTitleTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { title.value = it }
        }
    }

    val onSubTitleTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { subTitle.value = it }
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
}