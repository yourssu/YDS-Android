package com.yourssu.storybook.compose.baseScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.design.system.compose.atom.BoxButtonSize
import com.yourssu.design.system.compose.atom.BoxButtonType

/**
 * 스토리북의 각 컴포넌트 테스트 화면의 기반이 되는 함수입니다.
 *
 * @param S 사이즈 열거형
 * @param T 버튼 타입 열거형
 * @param description 컴포넌트의 이름
 * @param showXX XX 속성 아이템을 리스트에 추가할지 여부
 * @param sizeEnumValues S 타입 열거형 배열 반환하는 함수(values) (kotlin 1.9 업데이트 시 enumEntries로 대체 예정)
 * @param typeEnumValues T 타입 열거형 배열 반환하는 함수
 * @param sampleContent 샘플이 될 컴포넌트
 *
 * @see [StoryBookConfig]
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
    sampleContent: @Composable (StoryBookConfig<S, T>) -> Unit,
) {
}


@Composable
fun StoryBookScreen(
    description: String? = null,
    showText: Boolean = false,
    showTypo: Boolean = false,
    showRounding: Boolean = false,
    showIsPoint: Boolean = false,
    showIsWarn: Boolean = false,
    showIsDisable: Boolean = false,
    showIcons: Boolean = false,
    showItemColor: Boolean = false,
    sampleContent: @Composable (StoryBookConfig<Nothing, Nothing>) -> Unit,
) {
    StoryBookScreen(
        description = description,
        showText = showText,
        showTypo = showTypo,
        showRounding = showRounding,
        showSize = false,
        showButtonType = false,
        showIsPoint = showIsPoint,
        showIsWarn = showIsWarn,
        showIsDisable = showIsDisable,
        showIcons = showIcons,
        showItemColor = showItemColor,
        sizeEnumValues = null,
        typeEnumValues = null,
        sampleContent = sampleContent,
    )
}

@Preview
@Composable
fun StoryBookScreenPreview() {
    YdsTheme {
        StoryBookScreen<BoxButtonSize, BoxButtonType>(
            showText = true,
            showTypo = true,
            showRounding = true,
            showSize = true,
            showButtonType = true,
            showIsPoint = true,
            showIsWarn = true,
            showIsDisable = true,
            showIcons = true,
            showItemColor = true,
            sizeEnumValues = BoxButtonSize::values,
            typeEnumValues = BoxButtonType::values,
        ) { config ->
            BoxButton(
                onClick = { /*TODO*/ },
                text = config.text,
                leftIcon = config.leftIcon,
                rightIcon = config.rightIcon,
                isDisabled = config.isDisabled,
                isWarned = config.isWarned,
                sizeType = config.size ?: BoxButtonSize.Medium,
                buttonType = config.type ?: BoxButtonType.Line,
            )
        }
    }
}