package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yourssu.yds_ui_tester.databinding.ActivityBoxButtonBinding

class BoxButtonActivity : AppCompatActivity() {
    lateinit var binding: ActivityBoxButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_box_button)
    }
}