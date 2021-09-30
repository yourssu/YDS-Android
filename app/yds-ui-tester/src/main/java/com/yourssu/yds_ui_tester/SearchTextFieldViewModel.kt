package com.yourssu.yds_ui_tester

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchTextFieldViewModel : ViewModel() {

    private var text = ""
    private val _placeholderText = MutableLiveData("")
    val placeholderText: LiveData<String> = _placeholderText
    private val _focusTestText = MutableLiveData<String>()
    val focusTestText = _focusTestText

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        text = s.toString()
    }

    fun placeholderTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _placeholderText.value = s.toString()
    }

    fun setFocusTestText(text: String) {
        _focusTestText.value = text
    }
}