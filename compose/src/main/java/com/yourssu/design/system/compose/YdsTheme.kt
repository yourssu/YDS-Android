package com.yourssu.design.system.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.yourssu.design.system.compose.foundation.LocalYdsColorScheme
import com.yourssu.design.system.compose.foundation.LocalYdsTypography
import com.yourssu.design.system.compose.rule.Border
import com.yourssu.design.system.compose.rule.LocalYdsBorder
import com.yourssu.design.system.compose.foundation.YdsColorScheme
import com.yourssu.design.system.compose.foundation.YdsTypography
import com.yourssu.design.system.compose.foundation.darkColorScheme
import com.yourssu.design.system.compose.foundation.getYDSTypography
import com.yourssu.design.system.compose.foundation.lightColorScheme
import com.yourssu.design.system.compose.rule.LocalYdsRounding

@Composable
fun YdsTheme(
    typography: YdsTypography = getYDSTypography(),
    rounding: Shapes = YdsTheme.rounding,
    border: Border = YdsTheme.border,
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
        LocalYdsBorder provides border,
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

    val rounding: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsRounding.current

    val border: Border
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsBorder.current
}