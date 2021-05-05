package com.yourssu.yds_ui_tester.button

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.yourssu.yds_android.button.TextButton
import com.yourssu.yds_android.util.dpToPx
import com.yourssu.yds_android.util.safeGetColor
import com.yourssu.yds_ui_tester.R

class TextButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_button)

        val buttonWidth = baseContext.dpToPx(160)

        val rootLayout = findViewById<ViewGroup>(R.id.rootLayout)
        val fromXmlTextButton = findViewById<TextButton>(R.id.text_button_from_xml)
        fromXmlTextButton.setOnClickListener {
            Toast.makeText(baseContext, "Click Button", Toast.LENGTH_SHORT).show()
        }

        rootLayout.addView(FrameLayout(this).apply {
            addView(
                TextButton.createButton(context, TextButton.Style.Medium).apply {
                    text = "런타임 버튼1"
                    setTextColor(context.safeGetColor(R.color.yds_aqua_100))
                    isCompleted = true
                },
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                })
        }, LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT))
    }
}