package com.yourssu.storybook

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yourssu.storybook.transform.ActivityAnimType

abstract class BaseActivity: AppCompatActivity() {
    abstract var animationType: ActivityAnimType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(animationType.startInAnim, animationType.startOutAnim)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        overridePendingTransition(animationType.startInAnim, animationType.startOutAnim)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(animationType.endInAnim, animationType.endOutAnim)
    }
}