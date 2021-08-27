package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yourssu.yds_ui_tester.databinding.ActivityCheckBoxBinding

class CheckBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_box)
    }
}