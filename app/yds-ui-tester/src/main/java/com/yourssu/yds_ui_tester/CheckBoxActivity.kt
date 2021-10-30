package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.Checkbox
import com.yourssu.design.system.language.checkbox
import com.yourssu.yds_ui_tester.databinding.ActivityCheckBoxBinding

class CheckBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_box)

        binding.buttonEnabled.setOnClickListener {
            binding.check.apply {
                isDisabled = false
            }
        }

        binding.buttonDisabled.setOnClickListener {
            binding.check.apply {
                isDisabled = true
            }
        }

        binding.buttonGenerate.setOnClickListener {
            binding.test.checkbox {
                isDisabled = false
                isSelected = false
                size = Checkbox.LARGE
                label = "텍스트"
            }
        }
    }
}