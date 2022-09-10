package com.yourssu.composedesignsystem.ui.theme.atom

import android.os.Parcelable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon
import com.yourssu.composedesignsystem.ui.theme.base.noRippleClickable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItemState(
    private val _text: String,
    @DrawableRes private val leftIcon: Int? = null,
    @DrawableRes private val rightIcon: Int? = null,
    private val isDisabled: Boolean = false
) : Parcelable {
    @IgnoredOnParcel
    var textState by mutableStateOf(_text)
    @IgnoredOnParcel
    var leftIconState by mutableStateOf(leftIcon)
    @IgnoredOnParcel
    var rightIconState by mutableStateOf(rightIcon)
    @IgnoredOnParcel
    var isDisabledState by mutableStateOf(isDisabled)
}

@Composable
fun rememberListItemState(
    text: String,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false
): ListItemState = rememberSaveable(
    text, leftIcon, rightIcon, isDisabled
) {
    ListItemState(text, leftIcon, rightIcon, isDisabled)
}

@Composable
fun ListItem(
    onClick: () -> Unit,
    state: ListItemState,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed && !state.isDisabledState) {
        YdsTheme.colors.bgPressed
    } else {
        YdsTheme.colors.bgNormal
    }

    Row(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(48.dp)
            .noRippleClickable(interactionSource) {
                if (!state.isDisabledState) {
                    onClick()
                }
            }
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))

        state.leftIconState?.let { leftIconId ->
            YdsIcon(id = leftIconId)
            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = state.textState,
            modifier = Modifier.weight(1.0f),
            style = YdsTheme.typography.body1
        )

        state.rightIconState?.let { rightIconId ->
            Spacer(Modifier.width(8.dp))
            YdsIcon(id = rightIconId)
        }

        Spacer(Modifier.width(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    var count by remember { mutableStateOf(0) }
    val listItemState = rememberListItemState(
        text = "로그아웃",
        rightIcon = R.drawable.ic_ground_filled
    )

    ListItem(
        onClick = { Log.d("test", "ListItemPreview: ${count++}") },
        state = listItemState
    )
}