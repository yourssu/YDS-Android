package com.yourssu.yds_ui_tester

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.system.language.text
import com.yourssu.design.system.language.verticalLayout

class DesignSystemListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            verticalLayout {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                text {
                    text = "Text"
                    typo = Typo.Title1
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        startActivity(Intent(this@DesignSystemListActivity, MainActivity::class.java))
                    }
                }
                text {
                    text = "BottomSheet"
                    typo = Typo.Title1
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        startActivity(Intent(this@DesignSystemListActivity, MainActivity::class.java))
                    }
                }
                text {
                    text = "Divider"
                    typo = Typo.Title1
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        startActivity(Intent(this@DesignSystemListActivity, DividerActivity::class.java))
                    }
                }
                text {
                    text = "Toggle"
                    typo = Typo.Title1
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    gravity = Gravity.CENTER
                    setOnClickListener{
                        startActivity(Intent(this@DesignSystemListActivity, ToggleActivity::class.java))
                    }
                }
                text {
                    text = "BoxButton"
                    typo = Typo.Title1
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        startActivity(Intent(this@DesignSystemListActivity, BoxButtonActivity::class.java))
                    }
                }
            })
    }
}