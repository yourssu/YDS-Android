package com.yourssu.design.system.foundation

import com.yourssu.design.R

val semanticColors = listOf(
    ColorCategory("GroupName", listOf(
        Color("colorName", "lightValue / darkValue", R.color.transparent)
    )),
    ColorCategory("Background", listOf(
        Color("bgNormal", "white000 / black000", R.color.bgNormal),
        Color("bgElevated", "white000 / black000", R.color.bgElevated),
        Color("bgRecomment", "gray050 / realBlack", R.color.bgRecomment),
        Color("bgSelected", "gray100 / white000A5", R.color.bgSelected),
        Color("bgPressed", "gray100 / white000A5", R.color.bgPressed),
        Color("bgNormalDark", "realBlack / realBlack", R.color.bgNormalDark),
        Color("bgElevatedDark", "realBlack / realBlack", R.color.bgElevatedDark),
        Color("bgDimDark", "gray900A30 / black000A30", R.color.bgElevatedDark),
    )),
    ColorCategory("Text", listOf(
        Color("textPrimary", "black000 / gray900", R.color.textPrimary),
        Color("textSecondary", "gray900 / gray800", R.color.textSecondary),
        Color("textTertiary", "gray600 / gray600", R.color.textTertiary),
        Color("textDisabled", "gray500 / gray400", R.color.textDisabled),
        Color("textBright", "white000 / white000", R.color.textBright),
        Color("textPointed", "pointColor400 / pointColor400", R.color.textPointed),
        Color("textWarned", "warningRed400 / warningRed400", R.color.textWarned),
    )),
    ColorCategory("Dim", listOf(
        Color("dimNormal", "gray900A30 / black000A30", R.color.dimNormal),
        Color("dimThick", "gray900A70 / black000A70", R.color.dimThick),
        Color("dimThickBright", "white000A70 / white000A70", R.color.dimThickBright),
    )),
    ColorCategory("Border", listOf(
        Color("borderThin", "gray100 / gray100", R.color.borderThin),
        Color("borderNormal", "black000A10 / white000A10", R.color.borderNormal),
        Color("borderThick", "gray500 / gray500", R.color.borderThick),
    )),
    ColorCategory("Button", listOf(
        Color("buttonNormal", "gray700 / gray700", R.color.buttonNormal),
        Color("buttonNormalPressed", "gray600 / gray600", R.color.buttonNormalPressed),
        Color("buttonBG", "gray200 / gray200", R.color.buttonBG),
        Color("buttonEmojiBG", "gray100 / gray100", R.color.buttonEmojiBG),
        Color("buttonBright", "white000 / white000", R.color.buttonBright),
        Color("buttonDisabled", "gray500 / gray500", R.color.buttonDisabled),
        Color("buttonDisabledBG", "gray200 / gray200", R.color.buttonDisabledBG),
        Color("buttonPoint", "pointColor400 / pointColor400", R.color.buttonPoint),
        Color("buttonPointPressed", "pointColor300 / pointColor300", R.color.buttonPointPressed),
        Color("buttonPointBG", "pointColor050 / pointColor050", R.color.buttonPointBG),
        Color("buttonWarned", "warningRed400 / warningRed400", R.color.buttonWarned),
        Color("buttonWarnedPressed", "warningRed300 / warningRed300", R.color.buttonWarnedPressed),
        Color("buttonWarnedBG", "warningRed050 / warningRed050", R.color.buttonWarnedBG),
    )),
    ColorCategory("BottomBar", listOf(
        Color("bottomBarNormal", "gray600 / gray600", R.color.bottomBarNormal),
        Color("bottomBarSelected", "gray800 / gray800", R.color.bottomBarSelected),
    )),
    ColorCategory("inputField", listOf(
        Color("inputFieldNormal", "white000 / black000", R.color.inputFieldNormal),
        Color("inputFieldElevated", "gray100 / gray100", R.color.inputFieldElevated),
    )),
    ColorCategory("Toast", listOf(
        Color("toastBG", "gray700 / gray300", R.color.toastBG),
    )),
    ColorCategory("Tooltip", listOf(
        Color("tooltipPoint", "gray700 / gray400", R.color.tooltipBG),
        Color("tooltipPoint", "pointColor400 / pointColor400", R.color.tooltipPoint),
    )),
    ColorCategory("Pressed", listOf(
        Color("pressed", "black000A10 / white000A10", R.color.pressed),
    )),
    ColorCategory("Shadow", listOf(
        Color("shadowThin", "gray400 / gray400", R.color.shadowThin),
        Color("shadowNormal", "gray500 / gray500", R.color.shadowNormal),
    )),
    ColorCategory("MonoItem", listOf(
        Color("monoItemPrimary", "gray700", R.color.monoItemPrimary),
        Color("monoItemBG", "gray100", R.color.monoItemBG),
        Color("monoItemText", "gray800", R.color.monoItemText),
    )),
    ColorCategory("GreenItem", listOf(
        Color("greenItemPrimary", "green300", R.color.greenItemPrimary),
        Color("greenItemBG", "green050", R.color.greenItemBG),
        Color("greenItemText", "green800", R.color.greenItemText),
    )),
    ColorCategory("EmeraldItem", listOf(
        Color("emeraldItemPrimary", "emerald300", R.color.emeraldItemPrimary),
        Color("emeraldItemBG", "emerald050", R.color.emeraldItemBG),
        Color("emeraldItemText", "emerald800", R.color.emeraldItemText),
    )),
    ColorCategory("AquaItem", listOf(
        Color("aquaItemPrimary", "aqua300", R.color.aquaItemPrimary),
        Color("aquaItemBG", "aqua050", R.color.aquaItemBG),
        Color("aquaItemText", "aqua700", R.color.aquaItemText),
    )),
    ColorCategory("BlueItem", listOf(
        Color("blueItemPrimary", "blue300", R.color.blueItemPrimary),
        Color("blueItemBG", "blue050", R.color.blueItemBG),
        Color("blueItemText", "blue700", R.color.blueItemText),
    )),
    ColorCategory("IndigoItem", listOf(
        Color("indigoItemPrimary", "indigo300", R.color.indigoItemPrimary),
        Color("indigoItemBG", "indigo050", R.color.indigoItemBG),
        Color("indigoItemText", "indigo400", R.color.indigoItemText),
    )),
    ColorCategory("VioletItem", listOf(
        Color("violetItemPrimary", "violet300", R.color.violetItemPrimary),
        Color("violetItemBG", "violet050", R.color.violetItemBG),
        Color("violetItemText", "violet400", R.color.violetItemText),
    )),
    ColorCategory("PurpleItem", listOf(
        Color("purpleItemPrimary", "purple300", R.color.purpleItemPrimary),
        Color("purpleItemBG", "purple050", R.color.purpleItemBG),
        Color("purpleItemText", "purple400", R.color.purpleItemText),
    )),
    ColorCategory("PinkItem", listOf(
        Color("pinkItemPrimary", "pink300", R.color.pinkItemPrimary),
        Color("pinkItemBG", "pink050", R.color.pinkItemBG),
        Color("pinkItemText", "pink600", R.color.pinkItemText),
    )),
)