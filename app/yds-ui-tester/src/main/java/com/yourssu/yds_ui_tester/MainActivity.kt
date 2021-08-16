package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import com.yourssu.design.system.atom.BottomSheet.Companion.bottomSheet
import com.yourssu.design.system.atom.Text.Companion.text
import com.yourssu.design.system.component.Toast
import com.yourssu.design.system.component.Toast.Companion.shortToast
import com.yourssu.design.system.component.Toast.Companion.toast
import com.yourssu.design.system.foundation.Typo
import com.yourssu.yds_ui_tester.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var triggerTextStyle = ObservableBoolean(false)

    private val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 27, 28)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        binding.button.setOnClickListener {
            triggerTextStyle.set(!triggerTextStyle.get())
            bottomSheet {
                list.forEach {
                    text {
                        width = ViewGroup.LayoutParams.MATCH_PARENT
                        gravity = Gravity.CENTER
                        typo = Typo.Title2
                        text = "$it"
                    }
                }
            }.show()
            binding.root
        }
        binding.aaged.setOnClickListener {
            toast("안녕", Toast.Short)
            shortToast("안녕")
            toast("안녕")
        }
    }
}