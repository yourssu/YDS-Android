package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.yourssu.yds_ui_tester.databinding.ActivityTextFieldBinding

class TextFieldActivity : AppCompatActivity() {

    private val viewModel: TextFieldViewModel by viewModels()

    private lateinit var binding: ActivityTextFieldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_field)
        val simpleTextField = binding.simpleTextField
        binding.toggleIsDisabled.setOnCheckedChangeListener { _, isChecked ->
            simpleTextField.isDisabled = isChecked
        }
        binding.toggleIsPositive.setOnCheckedChangeListener { _, isChecked ->
            simpleTextField.isPositive = isChecked
        }
        binding.toggleIsNegative.setOnCheckedChangeListener { _, isChecked ->
            simpleTextField.isNegative = isChecked
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setFocusTestText("focus test")
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