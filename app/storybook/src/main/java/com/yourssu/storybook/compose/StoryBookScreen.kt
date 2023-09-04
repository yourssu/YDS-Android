package com.yourssu.storybook.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.BottomSheet
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.design.system.compose.atom.BoxButtonSize
import com.yourssu.design.system.compose.atom.BoxButtonType
import com.yourssu.design.system.compose.atom.Picker
import com.yourssu.design.system.compose.atom.rememberYdsBottomSheetState
import com.yourssu.design.system.compose.base.ProvideTextStyle
import com.yourssu.design.system.compose.base.Text
import com.yourssu.design.system.compose.foundation.YdsTextStyle
import kotlinx.coroutines.launch

/**
 * @param S 사이즈 열거형
 * @param T 버튼 타입 열거형
 */
@OptIn(ExperimentalMaterialApi::class)
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
    sampleContent: @Composable StoryBookConfig<S, T>.() -> Unit
) {
    val sheetState = rememberYdsBottomSheetState()
    val scope = rememberCoroutineScope()

    val config = remember {
        StoryBookConfig<S, T>().apply {
            text = description ?: text
            typo = if (showTypo) typographies.values.first() else typo
        }
    }

    var pickerType by remember { mutableStateOf(PickerType.Typo) }
    var pickerFirstItem: Map<String, *> by remember { mutableStateOf(emptyMap<String, Unit>()) }

    LaunchedEffect(pickerType) {
        // pickerType이 바뀌면 pickerFirstItem 재지정
        pickerFirstItem = when (pickerType) {
            PickerType.Typo -> typographies
            PickerType.LeftIcon, PickerType.RightIcon -> icons
        }
    }

    BottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetContent = {
            val keys = pickerFirstItem.keys.toList()
            Picker(
                firstItemList = keys,
                onFirstItemChange = { idx ->
                    // 맨 처음 실행될 때 keys가 empty라서 아래에서 get할 때 IndexOutOfBounds 떠서 체크 필요
                    if (keys.isEmpty()) return@Picker
                    when (pickerType) {
                        PickerType.Typo -> config.typo = pickerFirstItem[keys[idx]] as YdsTextStyle
                        PickerType.LeftIcon -> config.leftIcon = pickerFirstItem[keys[idx]] as Int
                        PickerType.RightIcon -> config.rightIcon = pickerFirstItem[keys[idx]] as Int
                    }
                },
            )
        },
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(YdsTheme.colors.bgDimDark),
                contentAlignment = Alignment.Center,
            ) {
                config.sampleContent()
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProvideTextStyle(
                    value = YdsTheme.typography.subTitle2
                ) {
                    if (showText) TextConfig(config)

                    if (showTypo) TypoConfig(
                        showBottomSheet = {
                            pickerType = PickerType.Typo
                            scope.launch { sheetState.show() }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextConfig(config: StoryBookConfig<*, *>) {
    Text("Text")
    Spacer(Modifier.height(8.dp))
    // fixme: YdsTextField 구현 완료되면 교체
    TextField(value = config.text, onValueChange = { config.text = it })
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun TypoConfig(showBottomSheet: () -> Unit) {
    Text("Typo")
    BoxButton(onClick = showBottomSheet, text = "Typo")
}

@Preview(showSystemUi = true, name = "모든 리스트 테스트")
@Composable
fun StoryBookPreview() {
    YdsTheme {
        StoryBookScreen<BoxButtonSize, BoxButtonType>(
            description = "BoxButton",
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
        ) {
            Column {
                Text(
                    text = text,
                    style = typo!!,
                )
                BoxButton(onClick = { /*TODO*/ }, text = text)
            }
        }
    }
}
