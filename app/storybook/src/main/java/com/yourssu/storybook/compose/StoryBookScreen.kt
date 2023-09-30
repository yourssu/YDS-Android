package com.yourssu.storybook.compose

import androidx.compose.runtime.Composable

/**
 * @param S 사이즈 열거형
 * @param T 버튼 타입 열거형
 *
 * @see com.yourssu.storybook.compose.StoryBookConfig
 */
@Composable
fun <S : Enum<S>, T : Enum<T>> StoryBookScreen(
    description: String? = null,
    showText: Boolean = false,
    showTypo: Boolean = false,
    showRounding: Boolean = false,
    showSize: Boolean = false,
    showButtonType: Boolean = false,
    showIsPoint: Boolean = false,
    showIsWarn: Boolean = false,
    showIsDisable: Boolean = false,
    showIcons: Boolean = false,
    showItemColor: Boolean = false,
    sizeEnumValues: (() -> Array<S>)? = null,
    typeEnumValues: (() -> Array<T>)? = null,
    sampleContent: @Composable StoryBookConfig<S, T>.() -> Unit,
) {

}