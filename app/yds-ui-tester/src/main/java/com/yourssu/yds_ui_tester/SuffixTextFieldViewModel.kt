package com.yourssu.yds_ui_tester

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SuffixTextFieldViewModel: ViewModel() {
    companion object {
        private const val DEBOUNCE_TIME = 300L
        private const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[!-~₩]{8,100}\$"
    }

    private val _isPositive = MutableLiveData<Boolean>()
    val isPositive = _isPositive
    private val _isNegative = MutableLiveData<Boolean>()
    val isNegative = _isNegative
    private var password = ""
    private val _helperLabelText = MutableLiveData("")
    val helperLabelText: LiveData<String> = _helperLabelText
    private val _fieldLabelText = MutableLiveData("")
    val fieldLabelText: LiveData<String> = _fieldLabelText
    private val _placeholderText = MutableLiveData("")
    val placeholderText: LiveData<String> = _placeholderText
    private val _focusTestText = MutableLiveData<String>()
    val focusTestText = _focusTestText
    private val _suffixTestText = MutableLiveData("")
    val suffixTestText = _suffixTestText


    private val handler = Handler()
    private val validateRunnable = Runnable {
        when (validatePassword()) {
            InputState.NEGATIVE -> updateInputState(isPositive = false, isNegative = true)
            InputState.POSITIVE -> updateInputState(isPositive = true, isNegative = false)
            else -> updateInputState(isPositive = false, isNegative = false)
        }
    }

    fun onPasswordChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        handler.removeCallbacks(validateRunnable)
        password = s.toString()
        updateInputState(isPositive = false, isNegative = false)

        if (password.isEmpty()) {
            updateInputState(isPositive = false, isNegative = false)
        } else {
            handler.postDelayed(validateRunnable, DEBOUNCE_TIME)
        }
    }

    fun helperLabelTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _helperLabelText.value = s.toString()
    }

    fun placeholderTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _placeholderText.value = s.toString()
    }

    fun fieldLabelTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _fieldLabelText.value = s.toString()
    }

    fun suffixLabelTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _suffixTestText.value = s.toString()
    }

    fun setFocusTestText(text: String) {
        _focusTestText.value = text
    }

    override fun onCleared() {
        handler.removeCallbacks(validateRunnable)
        super.onCleared()
    }

    private fun validatePassword(): InputState {
        return if (Pattern.matches(PASSWORD_REGEX, password)) {
            InputState.POSITIVE
        } else {
            InputState.NEGATIVE
        }
    }

    private fun updateInputState(isPositive: Boolean, isNegative: Boolean) {
        _isPositive.value = isPositive
        _isNegative.value = isNegative
    }

    enum class InputState {
        ENABLED,
        POSITIVE,
        NEGATIVE
    }
}