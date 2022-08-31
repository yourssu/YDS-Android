package com.yourssu.composedesignsystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.yourssu.composedesignsystem.ui.theme.foundation.*
import com.yourssu.composedesignsystem.ui.theme.foundation.LocalYdsColorScheme
import com.yourssu.composedesignsystem.ui.theme.foundation.LocalYdsTypography
import com.yourssu.composedesignsystem.ui.theme.rule.Border
import com.yourssu.composedesignsystem.ui.theme.rule.LocalYdsBorder
import com.yourssu.composedesignsystem.ui.theme.rule.LocalYdsRounding

@Composable
fun YdsTheme(
    colors: YdsColorScheme = YdsTheme.colors,
    typography: YdsTypography = YdsTheme.typography,
    rounding: Shapes = YdsTheme.rounding,
    border: Border = YdsTheme.border,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalYdsColorScheme provides colors,
        LocalYdsTypography provides typography,
        LocalYdsRounding provides rounding,
        LocalYdsBorder provides border,
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

    val rounding: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsRounding.current

    val border: Border
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsBorder.current
}