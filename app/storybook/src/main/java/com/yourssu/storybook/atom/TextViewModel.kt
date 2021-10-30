package com.yourssu.storybook.atom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class TextViewModel(application: Application): BaseViewModel(application) {
    val textString = MutableLiveData<String>("Text")

    val typo: MutableLiveData<Int> = MutableLiveData(Typo.Title1)
    val typoText: MutableLiveData<String> = MutableLiveData("Title1")

    val onTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { textString.value = it }
        }
    }
}