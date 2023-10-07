package com.yourssu.storybook.compose.baseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.BottomSheet
import com.yourssu.design.system.compose.atom.rememberYdsBottomSheetState
import com.yourssu.design.system.compose.base.YdsText

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
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <S : Enum<S>, T : Enum<T>> StoryBookScreen(
    description: String,
    sizeEnumValues: () -> Array<S>? = { null },
    typeEnumValues: () -> Array<T>? = { null },
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
    sampleContent: @Composable (StoryBookConfig<S, T>) -> Unit,
) {
    val config = remember { StoryBookConfig<S, T>(text = description) }
    val sheetState = rememberYdsBottomSheetState()
    var pickerType by remember { mutableStateOf(PickerType.Typo) }

    BottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            StoryBookPicker(
                config = config,
                pickerType = pickerType,
            )
        },
        sheetState = sheetState,
    ) {

        Column(Modifier.fillMaxSize()) {
            // 컴포넌트 부분
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(YdsTheme.colors.bgDimDark),
                contentAlignment = Alignment.Center,
            ) {
                sampleContent(config)
            }

            // 컴포넌트 속성 리스트 부분
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                if (showText) {
                    TextConfig(config)
                }
                if (showTypo) {
                    TypoConfig(
                        openBottomSheet = {
                            pickerType = PickerType.Typo
                            sheetState.show()
                        },
                    )
                }

            }
        }
    }
}

@Composable
fun StoryBookScreen(
    description: String,
    showText: Boolean = false,
    showTypo: Boolean = false,
    showRounding: Boolean = false,
    showIsPoint: Boolean = false,
    showIsWarn: Boolean = false,
    showIsDisable: Boolean = false,
    showIcons: Boolean = false,
    showItemColor: Boolean = false,
    sampleContent: @Composable (StoryBookConfig<*, *>) -> Unit,
) {
    StoryBookScreen<Nothing, Nothing>(
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
        sampleContent = sampleContent,
    )
}

//@Preview(showSystemUi = true, name = "모든 리스트 아이템 테스트")
//@Composable
//fun StoryBookScreenPreview_AllList() {
//    YdsTheme {
//        StoryBookScreen<BoxButtonSize, BoxButtonType>(
//            description = "BoxButton",
//            showText = true,
//            showTypo = true,
//            showRounding = true,
//            showSize = true,
//            showButtonType = true,
//            showIsPoint = true,
//            showIsWarn = true,
//            showIsDisable = true,
//            showIcons = true,
//            showItemColor = true,
//            sizeEnumValues = BoxButtonSize::values,
//            typeEnumValues = BoxButtonType::values,
//        ) { config ->
//            BoxButton(
//                onClick = { /*TODO*/ },
//                text = config.text,
//                leftIcon = config.leftIcon,
//                rightIcon = config.rightIcon,
//                isDisabled = config.isDisabled,
//                isWarned = config.isWarned,
//                sizeType = config.size,
//                buttonType = config.type,
//            )
//        }
//    }
//}

@Preview(showSystemUi = true, name = "YdsText 스크린 샘플")
@Composable
fun StoryBookScreenPreview_YdsText() {
    YdsTheme {
        StoryBookScreen(
            description = "YdsText",
            showText = true,
            showTypo = true,
        ) { config ->
            YdsText(
                text = config.text,
                style = config.typo,
            )
            config.size
        }
    }
}