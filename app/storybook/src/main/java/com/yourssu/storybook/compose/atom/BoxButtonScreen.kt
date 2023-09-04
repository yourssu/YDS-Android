package com.yourssu.storybook.compose.atom

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.design.system.compose.atom.BoxButtonSize
import com.yourssu.design.system.compose.atom.BoxButtonType
import com.yourssu.design.system.compose.rule.YdsRounding
import com.yourssu.storybook.compose.StoryBookScreen

@Composable
fun BoxButtonScreen(navController: NavHostController) {
    StoryBookScreen<BoxButtonSize, BoxButtonType>(
        description = "BoxButton",
        showText = true,
        showRounding = true,
        showSize = true,
        showButtonType = true,
        showIsWarn = true,
        showIsDisable = true,
        showIcons = true,
        sizeEnumValues = BoxButtonSize::values,
        typeEnumValues = BoxButtonType::values,
    ) {
        BoxButton(
            onClick = { },
            text = text,
            rounding = rounding ?: YdsRounding.Medium,
            sizeType = size ?: BoxButtonSize.Large,
            buttonType = type ?: BoxButtonType.Filled,
            isWarned = isWarned,
            isDisabled = isDisabled,
            leftIcon = leftIcon,
            rightIcon = rightIcon,
        )
    }
}