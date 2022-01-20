package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.foundation.Typo
import com.yourssu.yds_ui_tester.databinding.ActivityCheckBoxBinding
import com.yourssu.yds_ui_tester.databinding.ActivityEditTextBinding

class EditTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_text)
    }
}