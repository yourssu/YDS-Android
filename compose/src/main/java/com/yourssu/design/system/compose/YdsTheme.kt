package com.yourssu.design.system.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.yourssu.design.system.compose.foundation.LocalYdsColorScheme
import com.yourssu.design.system.compose.foundation.LocalYdsRounding
import com.yourssu.design.system.compose.foundation.LocalYdsTypography
import com.yourssu.design.system.compose.foundation.YdsColorScheme
import com.yourssu.design.system.compose.foundation.YdsRounding
import com.yourssu.design.system.compose.foundation.YdsTypography
import com.yourssu.design.system.compose.foundation.darkColorScheme
import com.yourssu.design.system.compose.foundation.lightColorScheme

@Composable
fun YdsTheme(
    typography: YdsTypography = YdsTheme.typography,
    rounding: YdsRounding = YdsTheme.rounding,
    content: @Composable () -> Unit
) {
    val colors = if (isSystemInDarkTheme()) {
        darkColorScheme
    } else {
        lightColorScheme
    }
    CompositionLocalProvider(
        LocalYdsColorScheme provides colors,
        LocalYdsTypography provides typography,
        LocalYdsRounding provides rounding,
        content = content
    )
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

    val rounding: YdsRounding
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsRounding.current
}