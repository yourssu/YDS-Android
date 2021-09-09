package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yourssu.design.system.atom.Badge
import com.yourssu.design.system.foundation.Icon
import com.yourssu.yds_ui_tester.databinding.ActivityBadgeBinding

class BadgeActivity : AppCompatActivity() {
    lateinit var binding: ActivityBadgeBinding
    var colorData: Int = Badge.INDIGO
    var iconData: Int = Icon.ic_playcircle_filled
    var textData: String = "데이터바인딩"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_badge)

        binding.activity = this
    }
}