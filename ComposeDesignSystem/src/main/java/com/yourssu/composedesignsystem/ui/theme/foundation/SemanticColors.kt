package com.yourssu.composedesignsystem.ui.theme.foundation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.yourssu.composedesignsystem.ui.theme.*


data class YdsColorScheme(
    // Background
    val bgNormal: Color,
    val bgElevated: Color,
    val bgRecomment: Color,
    val bgSelected: Color,
    val bgPressed: Color,
    val bgNormalDark: Color,
    val bgElevatedDark: Color,
    val bgDimDark: Color,

    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textBright: Color,
    val textPointed: Color,
    val textWarned: Color,

    // Dim
    val dimNormal: Color,
    val dimThick: Color,
    val dimThickBright: Color,
    val dimPicker: Color,

    // Border
    val borderThin: Color,
    val borderNormal: Color,
    val borderThick: Color,

    // Button
    val buttonNormal: Color,
    val buttonNormalPressed: Color,
    val buttonBG: Color,
    val buttonEmojiBG: Color,
    val buttonBright: Color,
    val buttonDisabled: Color,
    val buttonDisabledBG: Color,
    val buttonPoint: Color,
    val buttonPointPressed: Color,
    val buttonPointBG: Color,
    val buttonWarned: Color,
    val buttonWarnedPressed: Color,
    val buttonWarnedBG: Color,

    // BottomBar
    val bottomBarNormal: Color,
    val bottomBarSelected: Color,

    // InputField
    val inputFieldNormal: Color,
    val inputFieldElevated: Color,

    // Toast
    val toastBG: Color,

    // Tooltip
    val tooltipBG: Color,
    val tooltipPoint: Color,

    // Pressed
    val pressed: Color,

    // Shadow
    val shadowThin: Color,
    val shadowNormal: Color,

    /***** ItemColor *****/
    // MonoItem
    val monoItemPrimary: Color,
    val monoItemBG: Color,
    val monoItemText: Color,

    // GreenItem
    val greenItemPrimary: Color,
    val greenItemBG: Color,
    val greenItemText: Color,

    // EmeraldItem
    val emeraldItemPrimary: Color,
    val emeraldItemBG: Color,
    val emeraldItemText: Color,

    // AquaItem
    val aquaItemPrimary: Color,
    val aquaItemBG: Color,
    val aquaItemText: Color,

    // BlueItem
    val blueItemPrimary: Color,
    val blueItemBG: Color,
    val blueItemText: Color,

    // IndigoItem
    val indigoItemPrimary: Color,
    val indigoItemBG: Color,
    val indigoItemText: Color,

    // VioletItem
    val violetItemPrimary: Color,
    val violetItemBG: Color,
    val violetItemText: Color,

    // PurpleItem
    val purpleItemPrimary: Color,
    val purpleItemBG: Color,
    val purpleItemText: Color,

    // PinkItem
    val pinkItemPrimary: Color,
    val pinkItemBG: Color,
    val pinkItemText: Color,
)

val lightColorScheme = YdsColorScheme(
    // Background
    bgNormal = White000,
    bgElevated = White000,
    bgRecomment = Gray050,
    bgSelected = Gray100,
    bgPressed = Gray100,
    bgNormalDark = RealBlack,
    bgElevatedDark = RealBlack,
    bgDimDark = Gray900A30,

    // Text
    textPrimary = Black000,
    textSecondary = Gray900,
    textTertiary = Gray600,
    textDisabled = Gray500,
    textBright = White000,
    textPointed = PointColor400,
    textWarned = WarningRed400,

    // Dim
    dimNormal = Gray900A30,
    dimThick = Gray900A70,
    dimThickBright = White000A70,
    dimPicker = White000A70,

    // Border
    borderThin = Gray100,
    borderNormal = Black000A10,
    borderThick = Gray500,

    // Button
    buttonNormal = Gray700,
    buttonNormalPressed = Gray600,
    buttonBG = Gray200,
    buttonEmojiBG = Gray100,
    buttonBright = White000,
    buttonDisabled = Gray500,
    buttonDisabledBG = Gray200,
    buttonPoint = PointColor400,
    buttonPointPressed = PointColor300,
    buttonPointBG = PointColor050,
    buttonWarned = WarningRed400,
    buttonWarnedPressed = WarningRed300,
    buttonWarnedBG = WarningRed050,

    // BottomBar
    bottomBarNormal = Gray600,
    bottomBarSelected = Gray800,

    // InputField,
    inputFieldNormal = White000,
    inputFieldElevated = Gray100,

    // Toast
    toastBG = Gray800,

    // Tooltip
    tooltipBG = Gray700,
    tooltipPoint = PointColor400,

    // Pressed
    pressed = Gray900A5,

    // Shadow
    shadowThin = Gray400,
    shadowNormal = Gray500,

    // MonoItem
    monoItemPrimary = Gray700,
    monoItemBG = Gray100,
    monoItemText = Gray800,

    // GreenItem
    greenItemPrimary = Green300,
    greenItemBG = Green050,
    greenItemText = Green800,

    // EmeraldItem
    emeraldItemPrimary = Emerald300,
    emeraldItemBG = Emerald050,
    emeraldItemText = Emerald800,

    // AquaItem
    aquaItemPrimary = Aqua300,
    aquaItemBG = Aqua050,
    aquaItemText = Aqua700,

    // BlueItem
    blueItemPrimary = Blue300,
    blueItemBG = Blue050,
    blueItemText = Blue700,

    // IndigoItem,
    indigoItemPrimary = Indigo300,
    indigoItemBG = Indigo050,
    indigoItemText = Indigo400,

    // VioletItem
    violetItemPrimary = Violet300,
    violetItemBG = Violet050,
    violetItemText = Violet400,

    // PurpleItem,
    purpleItemPrimary = Purple300,
    purpleItemBG = Purple050,
    purpleItemText = Purple400,

    // PinkItem
    pinkItemPrimary = Pink300,
    pinkItemBG = Pink050,
    pinkItemText = Pink600
)

// TODO: darkTheme 컬러 대응 해줘야함.
val darkColorScheme = YdsColorScheme(
    // Background
    bgNormal = Black000,
    bgElevated = Black000,
    bgRecomment = RealBlack,
    bgSelected = White000A5,
    bgPressed = White000A5,
    bgNormalDark = RealBlack,
    bgElevatedDark = RealBlack,
    bgDimDark = Black000A30,

    // Text
    textPrimary = Gray900,
    textSecondary = Gray800,
    textTertiary = Gray600,
    textDisabled = Gray500,
    textBright = White000,
    textPointed = PointColor400,
    textWarned = WarningRed400,

    // Dim
    dimNormal = Black000A30,
    dimThick = Black000A70,
    dimThickBright = White000A70,
    dimPicker = Black000A70,

    // Border
    borderThin = Gray100,
    borderNormal = White000A10,
    borderThick = Gray500,

    // Button
    buttonNormal = Gray700,
    buttonNormalPressed = Gray600,
    buttonBG = Gray200,
    buttonEmojiBG = Gray100,
    buttonBright = White000,
    buttonDisabled = Gray500,
    buttonDisabledBG = Gray200,
    buttonPoint = PointColor400,
    buttonPointPressed = PointColor300,
    buttonPointBG = PointColor050,
    buttonWarned = WarningRed400,
    buttonWarnedPressed = WarningRed300,
    buttonWarnedBG = WarningRed050,

    // BottomBar
    bottomBarNormal = Gray600,
    bottomBarSelected = Gray800,

    // InputField,
    inputFieldNormal = White000,
    inputFieldElevated = Gray100,

    // Toast
    toastBG = Gray800,

    // Tooltip
    tooltipBG = Gray700,
    tooltipPoint = PointColor400,

    // Pressed
    pressed = Gray900A5,

    // Shadow
    shadowThin = Gray400,
    shadowNormal = Gray500,

    // MonoItem
    monoItemPrimary = Gray700,
    monoItemBG = Gray100,
    monoItemText = Gray800,

    // GreenItem
    greenItemPrimary = Green300,
    greenItemBG = Green050,
    greenItemText = Green800,

    // EmeraldItem
    emeraldItemPrimary = Emerald300,
    emeraldItemBG = Emerald050,
    emeraldItemText = Emerald800,

    // AquaItem
    aquaItemPrimary = Aqua300,
    aquaItemBG = Aqua050,
    aquaItemText = Aqua700,

    // BlueItem
    blueItemPrimary = Blue300,
    blueItemBG = Blue050,
    blueItemText = Blue700,

    // IndigoItem,
    indigoItemPrimary = Indigo300,
    indigoItemBG = Indigo050,
    indigoItemText = Indigo400,

    // VioletItem
    violetItemPrimary = Violet300,
    violetItemBG = Violet050,
    violetItemText = Violet400,

    // PurpleItem,
    purpleItemPrimary = Purple300,
    purpleItemBG = Purple050,
    purpleItemText = Purple400,

    // PinkItem
    pinkItemPrimary = Pink300,
    pinkItemBG = Pink050,
    pinkItemText = Pink600
)

internal val LocalYdsColorScheme = staticCompositionLocalOf { lightColorScheme }