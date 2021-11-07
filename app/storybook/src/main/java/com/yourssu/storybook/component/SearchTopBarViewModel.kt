package com.yourssu.storybook.component

import android.app.Application
import android.view.KeyEvent
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yourssu.design.system.atom.Toggle
import com.yourssu.storybook.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SearchTopBarViewModel(application: Application): BaseViewModel(application) {
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _textInSearchTopBar = MutableLiveData("")
    val textInSearchTopBar: LiveData<String> = _textInSearchTopBar
    private val _placeholderText = MutableLiveData("")
    val placeholderText: LiveData<String> = _placeholderText
    val initText = "initText"
    private val _isDisable = MutableLiveData(false)
    val isDisable: LiveData<Boolean> = _isDisable
    val enableSelectListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            _isDisable.value = boolean
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _textInSearchTopBar.value = s.toString()
    }

    fun onPlaceholderTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _placeholderText.value = s.toString()
    }

    fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        event(Event.KeyboardAction(actionId))
        return true
    }

    fun onLeftArrowButtonClicked() {
        event(Event.LeftArrowButtonClicked)
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object LeftArrowButtonClicked: Event()
        class KeyboardAction(val actionId: Int): Event()
    }
}