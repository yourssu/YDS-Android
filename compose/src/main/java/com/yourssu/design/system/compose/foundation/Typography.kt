package com.yourssu.design.system.compose.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse
import com.yourssu.design.system.compose.R

val fonts = FontFamily(
    Font(R.font.spoqa_han_sans_neo_regular, FontWeight.Normal),
    Font(R.font.spoqa_han_sans_neo_medium, FontWeight.Medium),
    Font(R.font.spoqa_han_sans_neo_bold, FontWeight.Bold),
)

/**
 * 폰트 사이즈를 Dp로 표현하기 위해 사용합니다.
 *
 * Text에 적용될 때는 `toTextStyle()`로 변환하여 사용됩니다.
 *
 * @see com.yourssu.design.system.compose.base.YdsText
 */
@Immutable
data class YdsTextStyle(
    val fontFamily: FontFamily = fonts,
    val fontWeight: FontWeight = FontWeight.Medium,
    val fontSize: Dp = Dp.Unspecified,
    val lineHeight: Dp = Dp.Unspecified,
    val color: Color = Color.Unspecified,
) {
    @Composable
    fun toTextStyle() = TextStyle(
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = with(LocalDensity.current) { fontSize.toSp() },
        lineHeight = with(LocalDensity.current) { lineHeight.toSp() },
        color = color,
    )

    fun merge(other: YdsTextStyle?): YdsTextStyle {
        if (other == null || other == Default) return this
        return YdsTextStyle(
            fontFamily = other.fontFamily,
            fontWeight = other.fontWeight,
            fontSize = this.fontSize.takeOrElse { other.fontSize },
            lineHeight = this.lineHeight.takeOrElse { other.lineHeight },
            color = this.color.takeOrElse { other.color }
        )
    }

    companion object {
        @Stable
        val Default = YdsTextStyle()
    }
}

@Immutable
data class YdsTypography(
    val title1: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.dp,
        lineHeight = 36.4f.dp,
    ),
    val title2: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.dp,
        lineHeight = 31.2f.dp,
    ),
    val title3: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.dp,
        lineHeight = 26.dp,
    ),
    val subTitle1: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.dp,
        lineHeight = 26.dp,
    ),
    val subTitle2: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.dp,
        lineHeight = 20.8f.dp,
    ),
    val subTitle3: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.dp,
        lineHeight = 18.2f.dp,
    ),
    val body1: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.dp,
        lineHeight = 22.5f.dp,
    ),
    val body2: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.dp,
        lineHeight = 21f.dp,
    ),
    val button0: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.dp,
        lineHeight = 22.4f.dp,
    ),
    val button1: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.dp,
        lineHeight = 22.4f.dp,
    ),
    val button2: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.dp,
        lineHeight = 18.2f.dp,
    ),
    val button3: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.dp,
        lineHeight = 18.2f.dp,
    ),
    val button4: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.dp,
        lineHeight = 16.8f.dp,
    ),
    val caption0: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.dp,
        lineHeight = 15.6f.dp,
    ),
    val caption1: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.dp,
        lineHeight = 15.6f.dp,
    ),
    val caption2: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.dp,
        lineHeight = 14.3f.dp,
    ),
    val display1: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 40.dp,
        lineHeight = 52.dp,
    ),
    val display2: YdsTextStyle = YdsTextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.dp,
        lineHeight = 41.6f.dp,
    ),
)

internal val LocalYdsTypography = staticCompositionLocalOf { YdsTypography() }
