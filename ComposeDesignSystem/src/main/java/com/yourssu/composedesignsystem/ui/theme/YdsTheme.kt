package com.yourssu.composedesignsystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.yourssu.composedesignsystem.ui.theme.foundation.*
import com.yourssu.composedesignsystem.ui.theme.foundation.LocalYdsColorScheme
import com.yourssu.composedesignsystem.ui.theme.foundation.LocalYdsTypography

@Composable
fun YdsTheme(
    colors: YdsColorScheme = YdsTheme.colors,
    typography: YdsTypography = YdsTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalYdsTypography provides typography,
        LocalYdsColorScheme provides colors,
        content = content
    )
}

object YdsTheme {
    val colors: YdsColorScheme
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()) {
            darkColorScheme
        } else {
            lightColorScheme
        }

    val typography: YdsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsTypography.current
}