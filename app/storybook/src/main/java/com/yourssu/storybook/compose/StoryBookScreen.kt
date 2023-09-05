package com.yourssu.storybook.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.yourssu.design.system.compose.atom.Toggle
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
    sampleContent: @Composable StoryBookConfig<S, T>.() -> Unit,
) {
    val sheetState = rememberYdsBottomSheetState()
    val scope = rememberCoroutineScope()

    val config = remember {
        StoryBookConfig<S, T>().apply {
            text = description ?: text
            if (showTypo) typo = typographies.values.first()
            if (showIcons) {
                leftIconText = icons.keys.first()
                rightIconText = icons.keys.first()
            }
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
            StoryBookPicker(
                config = config,
                pickerFirstItem = pickerFirstItem,
                pickerType = pickerType,
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                ProvideTextStyle(
                    value = YdsTheme.typography.subTitle2,
                ) {
                    if (showText) {
                        TextConfig(config)
                    }
                    if (showTypo) {
                        TypoConfig(
                            showBottomSheet = {
                                pickerType = PickerType.Typo
                                scope.launch { sheetState.show() }
                            },
                        )
                    }
                    if (showIcons) {
                        IconsConfig(
                            config = config,
                            setLeft = { pickerType = PickerType.LeftIcon },
                            setRight = { pickerType = PickerType.RightIcon },
                            showBottomSheet = { scope.launch { sheetState.show() }}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StoryBookPicker(
    config: StoryBookConfig<*, *>,
    pickerFirstItem: Map<String, *>,
    pickerType: PickerType,
) {
    val keys = pickerFirstItem.keys.toList()

    when (pickerType) {
        PickerType.Typo -> {
            Picker(
                firstItemList = keys,
                onFirstItemChange = { idx ->
                    // 맨 처음 실행될 때 keys가 empty라서 아래에서 get할 때 IndexOutOfBounds 떠서 체크 필요
                    if (keys.isEmpty()) return@Picker
                    config.typo = pickerFirstItem[keys[idx]] as YdsTextStyle
                },
            )
        }

        PickerType.LeftIcon -> {
            Picker(
                firstItemList = keys,
                onFirstItemChange = { idx ->
                    if (keys.isEmpty()) return@Picker
                    config.leftIconText = keys[idx]
                    config.leftIcon = if (config.leftIconToggle) {
                        pickerFirstItem[config.leftIconText] as Int
                    } else {
                        null
                    }
                },
            )
        }

        PickerType.RightIcon -> {
            Picker(
                firstItemList = keys,
                onFirstItemChange = { idx ->
                    if (keys.isEmpty()) return@Picker
                    config.rightIconText = keys[idx]
                    config.rightIcon = if (config.rightIconToggle) {
                        pickerFirstItem[config.rightIconText] as Int
                    } else {
                        null
                    }
                },
            )
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
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun TypoConfig(showBottomSheet: () -> Unit) {
    Text("Typo")
    Spacer(Modifier.height(8.dp))
    BoxButton(
        text = "Typo",
        onClick = showBottomSheet,
        buttonType = BoxButtonType.Line,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(16.dp))
}

@Composable
fun IconsConfig(
    config: StoryBookConfig<*, *>,
    setLeft: () -> Unit,
    setRight: () -> Unit,
    showBottomSheet: () -> Unit,
) {
    // LeftIcon
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("LeftIcon")
        Toggle(checked = config.leftIconToggle, onCheckedChange = { config.leftIconToggle = it })
    }
    Spacer(Modifier.height(8.dp))
    BoxButton(
        text = config.leftIconText,
        onClick = { setLeft(); showBottomSheet() },
        buttonType = BoxButtonType.Line,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(4.dp))

    // RightIcon
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("RightIcon")
        Toggle(checked = config.rightIconToggle, onCheckedChange = { config.rightIconToggle = it })
    }
    Spacer(Modifier.height(8.dp))
    BoxButton(
        text = config.rightIconText,
        onClick = { setRight(); showBottomSheet() },
        buttonType = BoxButtonType.Line,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(16.dp))
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
                BoxButton(
                    onClick = {},
                    text = text,
                    leftIcon = leftIcon,
                    rightIcon = rightIcon,
                )
            }
        }
    }
}
