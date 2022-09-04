package com.yourssu.composedesignsystem.ui.theme.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yourssu.composedesignsystem.ui.theme.YdsTheme

@Composable
fun Color.alterColorIfPressed(isPressed: Boolean): Color {
    return if (isPressed) {
        when (this) {
            YdsTheme.colors.buttonNormal -> YdsTheme.colors.buttonNormalPressed
            YdsTheme.colors.buttonPoint -> YdsTheme.colors.buttonPointPressed
            YdsTheme.colors.buttonWarned -> YdsTheme.colors.buttonWarnedPressed
            else -> this
        }
    } else {
        this
    }
}