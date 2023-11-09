package com.yourssu.design.system.compose.rule

import androidx.compose.ui.graphics.Color
import com.yourssu.design.system.compose.foundation.lightColorScheme

// 백엔드와의 동일한 컬러값 통신을 위해서 사용
enum class ItemColor {
    Mono,
    Green,
    Emerald,
    Aqua,
    Blue,
    Indigo,
    Violet,
    Purple,
    Pink;

    val semanticColor: Color
        get() = when (this) {
            Mono -> lightColorScheme.monoItemBG
            Green -> lightColorScheme.greenItemBG
            Emerald -> lightColorScheme.emeraldItemBG
            Aqua -> lightColorScheme.aquaItemBG
            Blue -> lightColorScheme.blueItemBG
            Indigo -> lightColorScheme.indigoItemBG
            Violet -> lightColorScheme.violetItemBG
            Purple -> lightColorScheme.purpleItemBG
            Pink -> lightColorScheme.pinkItemBG
        }
}
