package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import com.yourssu.design.system.atom.Toggle
import com.yourssu.yds_ui_tester.databinding.ActivityToggleBinding

class ToggleActivity : AppCompatActivity() {
    lateinit var binding: ActivityToggleBinding

    var isDisabled = ObservableBoolean(false)
    var isSelected = ObservableBoolean(false)

    val selectedListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isSelected.set(boolean)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_toggle)
        binding.activity = this

        binding.buttonChangeDisabled.setOnClickListener {
            isDisabled.set(!isDisabled.get())
        }

        binding.buttonChangeSelected.setOnClickListener {
            isSelected.set(!isSelected.get())
        }
    }
}