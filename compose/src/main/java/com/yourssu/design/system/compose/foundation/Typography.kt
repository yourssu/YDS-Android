package com.yourssu.design.system.compose.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.yourssu.design.R

val fonts = FontFamily(
    Font(R.font.spoqa_han_sans_neo_regular, FontWeight.Normal),
    Font(R.font.spoqa_han_sans_neo_medium, FontWeight.Medium),
    Font(R.font.spoqa_han_sans_neo_bold, FontWeight.Bold)
)

@Immutable
data class YdsTypography(
    val title1: TextStyle = TextStyle.Default,
    val title2: TextStyle = TextStyle.Default,
    val title3: TextStyle = TextStyle.Default,
    val subTitle1: TextStyle = TextStyle.Default,
    val subTitle2: TextStyle = TextStyle.Default,
    val subTitle3: TextStyle = TextStyle.Default,
    val body1: TextStyle = TextStyle.Default,
    val body2: TextStyle = TextStyle.Default,
    val button0: TextStyle = TextStyle.Default,
    val button1: TextStyle = TextStyle.Default,
    val button2: TextStyle = TextStyle.Default,
    val button3: TextStyle = TextStyle.Default,
    val button4: TextStyle = TextStyle.Default,
    val caption0: TextStyle = TextStyle.Default,
    val caption1: TextStyle = TextStyle.Default,
    val caption2: TextStyle = TextStyle.Default,
    val display1: TextStyle = TextStyle.Default,
    val display2: TextStyle = TextStyle.Default,
)

internal val LocalYdsTypography = staticCompositionLocalOf { YdsTypography() }

@Composable
fun getYDSTypography() =
    YdsTypography(
        title1 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 28.dp(),
            lineHeight = 36.4f.dp()
        ),
        title2 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 24.dp(),
            lineHeight = 31.2f.dp()
        ),
        title3 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 20.dp(),
            lineHeight = 26.dp()
        ),
        subTitle1 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 20.dp(),
            lineHeight = 26.dp()
        ),
        subTitle2 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 16.dp(),
            lineHeight = 20.8f.dp()
        ),
        subTitle3 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 14.dp(),
            lineHeight = 18.2f.dp()
        ),
        body1 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = 15.dp(),
            lineHeight = 22.5f.dp()
        ),
        body2 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = 14.dp(),
            lineHeight = 21f.dp()
        ),
        button0 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 16.dp(),
            lineHeight = 22.4f.dp()
        ),
        button1 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 16.dp(),
            lineHeight = 22.4f.dp()
        ),
        button2 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 14.dp(),
            lineHeight = 18.2f.dp()
        ),
        button3 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 14.dp(),
            lineHeight = 18.2f.dp()
        ),
        button4 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 12.dp(),
            lineHeight = 16.8f.dp()
        ),
        caption0 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 12.dp(),
            lineHeight = 15.6f.dp()
        ),
        caption1 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 12.dp(),
            lineHeight = 15.6f.dp()
        ),
        caption2 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 11.dp(),
            lineHeight = 14.3f.dp()
        ),
        display1 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 40.dp(),
            lineHeight = 52.dp()
        ),
        display2 = TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 32.dp(),
            lineHeight = 41.6f.dp()
        )
    )


@Composable
fun Float.dp() = with(LocalDensity.current) { Dp(this@dp).toSp() }

@Composable
fun Int.dp() = with(LocalDensity.current) { Dp(this@dp.toFloat()).toSp() }
