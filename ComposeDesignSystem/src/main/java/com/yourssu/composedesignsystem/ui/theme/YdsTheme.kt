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
    darkTheme: Boolean = isSystemInDarkTheme(), // 추후에 제거
    typography: YdsTypography = YdsTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = lightColorScheme
    // TODO add  darkColorScheme
    CompositionLocalProvider(LocalYdsTypography provides typography) {
//    MaterialTheme(
//        colors = colors,
//        typography = typography,
//        shapes = Shapes,
//        content = content
//    )

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