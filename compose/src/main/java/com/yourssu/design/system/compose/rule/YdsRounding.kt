package com.yourssu.design.system.compose.rule

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

private val smallRounding = RoundedCornerShape(2.dp)
private val normalRounding = RoundedCornerShape(4.dp)
private val largeRounding = RoundedCornerShape(8.dp)

enum class YdsRounding(val shape: RoundedCornerShape) {
    Small(smallRounding),
    Medium(normalRounding),
    Large(largeRounding),
}
