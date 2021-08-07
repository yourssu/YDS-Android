package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import com.yourssu.yds_ui_tester.databinding.ActivityDividerBinding

class DividerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDividerBinding

    var isThick = ObservableBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_divider)

        binding.activity = this
        binding.buttonChangeThickness.setOnClickListener {
            val currentValue = isThick.get()
            isThick.set(!currentValue)
        }
    }
}