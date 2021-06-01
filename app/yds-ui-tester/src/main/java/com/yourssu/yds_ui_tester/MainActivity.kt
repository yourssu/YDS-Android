package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import com.yourssu.yds_android.foundation.Text
import com.yourssu.yds_ui_tester.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var triggerTextStyle = ObservableBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        binding.button.setOnClickListener {
            triggerTextStyle.set(!triggerTextStyle.get())
        }

        val textView = Text.createText(this, Text.YDSTextStyle.SUBTITLE1)
        textView.text = "bbbbbbbb"
        textView.setTextColor(ResourcesCompat.getColor(resources, R.color.aquaTagBG, theme))
        binding.area.addView(textView)
    }
}