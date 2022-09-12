package com.yourssu.composedesignsystem.ui.theme.atom

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListToggleItemState(
    private val _text: String,
    private val _isSelected: Boolean,
    private val _isDisabled: Boolean
) : Parcelable {
    @IgnoredOnParcel
    var text by mutableStateOf(_text)
    @IgnoredOnParcel
    var isSelected by mutableStateOf(_isSelected)
    @IgnoredOnParcel
    var isDisabled by mutableStateOf(_isDisabled)

    val toggleState: ToggleState
        @Composable get() = rememberToggleState(isSelected = isSelected, isDisabled = isDisabled)
}

@Composable
fun rememberListToggleItemState(
    text: String,
    isSelected: Boolean = false,
    isDisabled: Boolean = false
): ListToggleItemState = rememberSaveable(text, isSelected, isDisabled) {
    ListToggleItemState(text, isSelected, isDisabled)
}

@Composable
fun ListToggleItem(
    state: ListToggleItemState,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(48.dp)
            .background(color = YdsTheme.colors.bgNormal),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))

        Text(
            text = state.text,
            modifier = Modifier.weight(1f),
            style = YdsTheme.typography.body1
        )

        Spacer(Modifier.width(8.dp))

        Toggle(
            state = state.toggleState,
            onCheckedChange = onCheckedChange
        )

        Spacer(Modifier.width(20.dp))
    }
}

@Preview
@Composable
fun ListToggleItemPreview() {
    // state2가 체크되어있지 않은 상태로 state1을 체크하면 state2가 같이 움직이는 버그 있음
    val state1 = rememberListToggleItemState(text = "로그아웃")
    val state2 = rememberListToggleItemState(text = "테스트", isSelected = true)
    YdsTheme {
        Column {
            ListToggleItem(state = state1, onCheckedChange = {
                state2.isDisabled = it
            })
            ListToggleItem(state = state2, onCheckedChange = {})
        }
    }
}