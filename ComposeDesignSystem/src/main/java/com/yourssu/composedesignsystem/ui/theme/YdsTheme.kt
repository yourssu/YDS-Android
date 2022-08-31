package com.yourssu.composedesignsystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.yourssu.composedesignsystem.ui.theme.*
import com.yourssu.composedesignsystem.ui.theme.foundation.LocalYdsTypography
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsTypography

/*private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)*/

@Composable
fun YdsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 추후에 제거
    typography: YdsTypography = YdsTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        //DarkColorPalette
    } else {
        //LightColorPalette
    }

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
    val typography: YdsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalYdsTypography.current
}