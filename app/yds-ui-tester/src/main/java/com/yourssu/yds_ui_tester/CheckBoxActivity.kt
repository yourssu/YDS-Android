package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.test.apply {
            isDisabled = false
            is_Selected = false
            size = 0
            text = "드디어"
        }

        binding.linear.checkBox {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isDisabled = false
            is_Selected = false
            size = 2
            text = "안녕하세요"
        }
        binding.linear.checkBox {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isDisabled = true
            is_Selected = true
            size = 0
            text = "안녕하세요"
        }
    }
}