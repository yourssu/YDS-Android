package com.yourssu.storybook.compose.baseScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.atom.BoxButton
import com.yourssu.design.system.compose.atom.BoxButtonType
import com.yourssu.design.system.compose.base.YdsText
import kotlinx.coroutines.launch

@Composable
fun TextConfig(config: StoryBookConfig<*, *>) {
    Column(Modifier.fillMaxWidth()) {
        YdsText("Text")
        Spacer(Modifier.height(8.dp))
        // fixme: YdsTextField 구현 완료되면 교체
        TextField(value = config.text, onValueChange = { config.text = it })
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun TypoConfig(openBottomSheet: suspend () -> Unit) {
    val scope = rememberCoroutineScope()
    Column {
        YdsText("Typo")
        Spacer(Modifier.height(8.dp))
        BoxButton(
            text = "Typo",
            onClick = { scope.launch { openBottomSheet() } },
            buttonType = BoxButtonType.Line,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
    }
}