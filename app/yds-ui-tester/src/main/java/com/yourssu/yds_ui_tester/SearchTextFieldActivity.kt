package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.Toggle
import com.yourssu.yds_ui_tester.databinding.ActivitySearchTextFieldBinding

class SearchTextFieldActivity : AppCompatActivity() {

    private val viewModel: SearchTextFieldViewModel by viewModels()

    private lateinit var binding: ActivitySearchTextFieldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_text_field)
        val simpleTextField = binding.simpleTextField
        binding.toggleIsDisabled.setOnSelectedListener(object : Toggle.SelectedListener {
            override fun onSelected(boolean: Boolean) {
                simpleTextField.isDisabled = boolean
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