package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.Toggle
import com.yourssu.yds_ui_tester.databinding.ActivitySuffixTextFieldBinding

class SuffixTextFieldActivity : AppCompatActivity() {

    private val viewModelSimple: SuffixTextFieldViewModel by viewModels()

    private lateinit var binding: ActivitySuffixTextFieldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_suffix_text_field)
        val simpleTextField = binding.simpleTextField
        binding.toggleIsDisabled.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                simpleTextField.isDisabled = boolean
            }
        })
        binding.toggleIsPositive.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                simpleTextField.isPositive = boolean
            }
        })
        binding.toggleIsNegative.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                simpleTextField.isNegative = boolean
            }
        })
        binding.viewModel = viewModelSimple
        binding.lifecycleOwner = this

        viewModelSimple.setFocusTestText("focus test")
    }

    override fun onResume() {
        super.onResume()

        setFocusAndShowKeyboard()
    }

    private fun setFocusAndShowKeyboard() {
        if (binding.giveMeFocus.requestFocus()) {
            binding.giveMeFocus.setSelection(binding.giveMeFocus.text.length)
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

}