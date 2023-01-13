package com.yourssu.design.compose.atom

import android.os.Parcelable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.R
import com.yourssu.design.compose.YdsTheme
import com.yourssu.design.compose.foundation.YdsIcon
import com.yourssu.design.compose.base.noRippleClickable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItemState(
    private val _text: String,
    @DrawableRes private val _leftIcon: Int? = null,
    @DrawableRes private val _rightIcon: Int? = null,
    private val _isDisabled: Boolean = false
) : Parcelable {
    @IgnoredOnParcel
    var text by mutableStateOf(_text)
    @IgnoredOnParcel
    var leftIcon by mutableStateOf(_leftIcon)
    @IgnoredOnParcel
    var rightIcon by mutableStateOf(_rightIcon)
    @IgnoredOnParcel
    var isDisabled by mutableStateOf(_isDisabled)
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
    val backgroundColor = if (isPressed && !state.isDisabled) {
        YdsTheme.colors.bgPressed
    } else {
        Color.Transparent
    }

    Row(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(48.dp)
            .noRippleClickable(interactionSource) {
                if (!state.isDisabled) {
                    onClick()
                }
            }
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))

        state.leftIcon?.let { leftIconId ->
            YdsIcon(id = leftIconId, tint = YdsTheme.colors.buttonNormal)
            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = state.text,
            modifier = Modifier.weight(1.0f),
            style = YdsTheme.typography.body1,
            color = YdsTheme.colors.textSecondary
        )

        state.rightIcon?.let { rightIconId ->
            Spacer(Modifier.width(8.dp))
            YdsIcon(id = rightIconId, tint = YdsTheme.colors.buttonNormal)
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