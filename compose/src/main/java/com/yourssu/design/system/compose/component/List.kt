package com.yourssu.design.system.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.ListItem
import com.yourssu.design.system.compose.atom.ListToggleItem
import com.yourssu.design.system.compose.base.YdsText

@Composable
fun List(
    modifier: Modifier = Modifier,
    subHeader: String? = null,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 8.dp),
        state = state
    ) {
        subHeader?.let {
            item {
                Box(
                    modifier = Modifier
                        .height(42.dp)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    YdsText(
                        text = it,
                        style = YdsTheme.typography.subTitle3
                    )
                }
            }
        }
        content()
    }
}

@Preview(name = "List")
@Composable
private fun PreviewList() {
    var checked1 by remember { mutableStateOf(false) }

    YdsTheme {
        List(
            subHeader = "My"
        ) {
            items(5) {
                ListItem(text = "비밀번호 변경", onClick = {}, rightIcon = R.drawable.ic_arrow_left_line)
            }
            item {
                ListToggleItem(
                    text = "알림 받기",
                    checked = checked1,
                    onCheckedChange = { checked1 = it })
            }
        }
    }
}
