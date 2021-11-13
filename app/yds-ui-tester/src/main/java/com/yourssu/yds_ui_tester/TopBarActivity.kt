package com.yourssu.yds_ui_tester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.yourssu.yds_ui_tester.databinding.ActivityTopBarBinding

class TopBarActivity : AppCompatActivity() {
    lateinit var binding: ActivityTopBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_bar)
    }
}