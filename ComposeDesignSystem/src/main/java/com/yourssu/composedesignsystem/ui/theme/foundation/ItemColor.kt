package com.yourssu.composedesignsystem.ui.theme.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import com.yourssu.composedesignsystem.ui.theme.YdsTheme

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

    @Composable
    fun getSemanticColor() = rememberUpdatedState(when (this) {
        Mono -> YdsTheme.colors.monoItemBG
        Green -> YdsTheme.colors.greenItemBG
        Emerald -> YdsTheme.colors.emeraldItemBG
        Aqua -> YdsTheme.colors.aquaItemBG
        Blue -> YdsTheme.colors.blueItemBG
        Indigo -> YdsTheme.colors.indigoItemBG
        Violet -> YdsTheme.colors.violetItemBG
        Purple -> YdsTheme.colors.purpleItemBG
        Pink -> YdsTheme.colors.pinkItemBG
    })
}