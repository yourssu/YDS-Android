package com.yourssu.design.system.compose.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class YdsColorScheme(
    // Background
    val bgNormal: Color = White000,
    val bgElevated: Color = White000,
    val bgRecomment: Color = Gray050,
    val bgSelected: Color = Gray100,
    val bgPressed: Color = Gray100,
    val bgNormalDark: Color = RealBlack,
    val bgElevatedDark: Color = RealBlack,
    val bgDimDark: Color = Gray900A30,

    // Text
    val textPrimary: Color = Black000,
    val textSecondary: Color = Gray900,
    val textTertiary: Color = Gray600,
    val textDisabled: Color = Gray500,
    val textBright: Color = White000,
    val textPointed: Color = PointColor400,
    val textWarned: Color = WarningRed400,

    // Dim
    val dimNormal: Color = Gray900A30,
    val dimThick: Color = Gray900A70,
    val dimThickBright: Color = White000A70,
    val dimPicker: Color = White000A70,

    // Border
    val borderThin: Color = Gray100,
    val borderNormal: Color = Black000A10,
    val borderThick: Color = Gray500,

    // Button
    val buttonNormal: Color = Gray700,
    val buttonNormalPressed: Color = Gray600,
    val buttonBG: Color = Gray200,
    val buttonEmojiBG: Color = Gray100,
    val buttonBright: Color = White000,
    val buttonDisabled: Color = Gray500,
    val buttonDisabledBG: Color = Gray200,
    val buttonPoint: Color = PointColor400,
    val buttonPointPressed: Color = PointColor300,
    val buttonPointBG: Color = PointColor050,
    val buttonWarned: Color = WarningRed400,
    val buttonWarnedPressed: Color = WarningRed300,
    val buttonWarnedBG: Color = WarningRed050,

    // BottomBar
    val bottomBarNormal: Color = Gray600,
    val bottomBarSelected: Color = Gray800,

    // InputField,
    val inputFieldNormal: Color = White000,
    val inputFieldElevated: Color = Gray100,

    // Toast
    val toastBG: Color = Gray800,

    // Tooltip
    val tooltipBG: Color = Gray700,
    val tooltipPoint: Color = PointColor400,

    // Pressed
    val pressed: Color = Gray900A5,

    // Shadow
    val shadowThin: Color = Gray400,
    val shadowNormal: Color = Gray500,

    // MonoItem
    val monoItemPrimary: Color = Gray700,
    val monoItemBG: Color = Gray100,
    val monoItemText: Color = Gray800,

    // GreenItem
    val greenItemPrimary: Color = Green300,
    val greenItemBG: Color = Green050,
    val greenItemText: Color = Green800,

    // EmeraldItem
    val emeraldItemPrimary: Color = Emerald300,
    val emeraldItemBG: Color = Emerald050,
    val emeraldItemText: Color = Emerald800,

    // AquaItem
    val aquaItemPrimary: Color = Aqua300,
    val aquaItemBG: Color = Aqua050,
    val aquaItemText: Color = Aqua700,

    // BlueItem
    val blueItemPrimary: Color = Blue300,
    val blueItemBG: Color = Blue050,
    val blueItemText: Color = Blue700,

    // IndigoItem,
    val indigoItemPrimary: Color = Indigo300,
    val indigoItemBG: Color = Indigo050,
    val indigoItemText: Color = Indigo400,

    // VioletItem
    val violetItemPrimary: Color = Violet300,
    val violetItemBG: Color = Violet050,
    val violetItemText: Color = Violet400,

    // PurpleItem,
    val purpleItemPrimary: Color = Purple300,
    val purpleItemBG: Color = Purple050,
    val purpleItemText: Color = Purple400,

    // PinkItem
    val pinkItemPrimary: Color = Pink300,
    val pinkItemBG: Color = Pink050,
    val pinkItemText: Color = Pink600
)

val lightColorScheme = YdsColorScheme()

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
    textPrimary = Gray900_night,
    textSecondary = Gray800_night,
    textTertiary = Gray600_night,
    textDisabled = Gray500_night,
    textBright = White000,
    textPointed = PointColor400_night,
    textWarned = WarningRed400_night,

    // Dim
    dimNormal = Black000A30,
    dimThick = Black000A70,
    dimThickBright = White000A70,
    dimPicker = Black000A70,

    // Border
    borderThin = Gray100_night,
    borderNormal = White000A10,
    borderThick = Gray500_night,

    // Button
    buttonNormal = Gray700_night,
    buttonNormalPressed = Gray600_night,
    buttonBG = Gray200_night,
    buttonEmojiBG = Gray100_night,
    buttonBright = White000,
    buttonDisabled = Gray500_night,
    buttonDisabledBG = Gray200_night,
    buttonPoint = PointColor400_night,
    buttonPointPressed = PointColor300_night,
    buttonPointBG = PointColor050_night,
    buttonWarned = WarningRed400_night,
    buttonWarnedPressed = WarningRed300_night,
    buttonWarnedBG = WarningRed050_night,

    // BottomBar
    bottomBarNormal = Gray600_night,
    bottomBarSelected = Gray800_night,

    // InputField,
    inputFieldNormal = Black000,
    inputFieldElevated = Gray100_night,

    // Toast
    toastBG = Gray300_night,

    // Tooltip
    tooltipBG = Gray400_night,
    tooltipPoint = PointColor400_night,

    // Pressed
    pressed = White000A10

    // Shadow * 다크모드에서는 Shadow를 사용하지 않습니다 *

    // 나머지 Item Color는 동일
)

internal val LocalYdsColorScheme = staticCompositionLocalOf { lightColorScheme }