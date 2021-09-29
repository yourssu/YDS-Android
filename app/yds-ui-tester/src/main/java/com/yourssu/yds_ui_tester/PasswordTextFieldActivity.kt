package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.Toggle
import com.yourssu.yds_ui_tester.databinding.ActivityPasswordTextFieldBinding

class PasswordTextFieldActivity : AppCompatActivity() {

    private val viewModel: PasswordTextFieldViewModel by viewModels()

    private lateinit var binding: ActivityPasswordTextFieldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_text_field)
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