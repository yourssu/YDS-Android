package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yourssu.yds_ui_tester.databinding.ActivityBadgeBinding

class BadgeActivity : AppCompatActivity() {
    lateinit var binding: ActivityBadgeBinding
    var colorData: String = "emerald"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_badge)

        binding.activity = this
    }
}