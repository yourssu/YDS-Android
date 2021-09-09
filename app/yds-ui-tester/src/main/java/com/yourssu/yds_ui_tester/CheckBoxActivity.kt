package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.CheckBox
import com.yourssu.design.system.atom.CheckBox.Companion.checkBox
import com.yourssu.yds_ui_tester.databinding.ActivityCheckBoxBinding

class CheckBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_box)

        binding.buttonEnabled.setOnClickListener {
            binding.test.apply {
                isDisabled = false
            }
        }

        binding.buttonDisabled.setOnClickListener {
            binding.test.apply {
                isDisabled = true
            }
        }

        binding.buttonGenerate.setOnClickListener {
            binding.linear.checkBox {
                isDisabled = true
                isSelected = false
                size = 3
                label = "두번째"
            }
        }

        binding.test.apply {
            isDisabled = false
            isSelected = false
            size = 3
            label = "드디어"
        }

        binding.linear.checkBox {
            isDisabled = true
            isSelected = true
            size = 1
            label = "첫번째"
        }
        binding.linear.checkBox {
            isDisabled = true
            isSelected = false
            size = 2
            label = "두번째"
        }
        binding.linear.checkBox {
            isDisabled = false
            isSelected = true
            size = 3
            label = "세번째"
        }
        binding.linear.checkBox {
            isDisabled = false
            isSelected = false
            size = 3
            label = "네번째"
        }
    }
}