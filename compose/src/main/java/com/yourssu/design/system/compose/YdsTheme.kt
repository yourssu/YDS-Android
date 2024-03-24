package com.yourssu.design.system.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.yourssu.design.system.compose.base.ProvideTextStyle
import com.yourssu.design.system.compose.foundation.LocalYdsColorScheme
import com.yourssu.design.system.compose.foundation.LocalYdsContentColor
import com.yourssu.design.system.compose.foundation.LocalYdsTypography
import com.yourssu.design.system.compose.foundation.YdsColorScheme
import com.yourssu.design.system.compose.foundation.YdsTypography
import com.yourssu.design.system.compose.foundation.darkColorScheme
import com.yourssu.design.system.compose.foundation.lightColorScheme

@Composable
fun YdsTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    typography: YdsTypography = YdsTheme.typography,
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkMode) {
        darkColorScheme
    } else {
        lightColorScheme
    }
    CompositionLocalProvider(
        LocalYdsColorScheme provides colors,
        LocalYdsTypography provides typography,
        LocalYdsContentColor provides colors.textPrimary,
    ) {
        ProvideTextStyle(value = typography.body1) {
            content()
        }
    }
}

object YdsTheme {
    val colors: YdsColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsColorScheme.current

    val typography: YdsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsTypography.current
}
