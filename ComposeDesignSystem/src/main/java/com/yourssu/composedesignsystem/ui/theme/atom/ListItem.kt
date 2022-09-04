package com.yourssu.composedesignsystem.ui.theme.atom

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.R
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.foundation.YdsIcon

data class ListItemState(
    private val text: String,
    @DrawableRes private val leftIcon: Int? = null,
    @DrawableRes private val rightIcon: Int? = null,
    private val isDisabled: Boolean = false,
    val interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    var textState by mutableStateOf(text)
    var leftIconState by mutableStateOf(leftIcon)
    var rightIconState by mutableStateOf(rightIcon)
    var isDisabledState by mutableStateOf(isDisabled)
}

@Composable
fun rememberListItemState(
    text: String,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): ListItemState = remember(
    text, leftIcon, rightIcon, isDisabled, interactionSource
) {
    ListItemState(text, leftIcon, rightIcon, isDisabled, interactionSource)
}

@Composable
fun ListItem(
    onClick: () -> Unit,
    state: ListItemState,
    modifier: Modifier = Modifier
) {
    val notClickableModifier = Modifier
        .then(modifier)
        .fillMaxWidth()
        .height(48.dp)

    Row(
        modifier = if (state.isDisabledState) {
            notClickableModifier
        } else {
            notClickableModifier.clickable { onClick() }
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        state.leftIconState?.let { leftIconId ->
            Spacer(Modifier.width(20.dp))
            YdsIcon(id = leftIconId)
        }

        Spacer(Modifier.width(8.dp))
        Text(
            text = state.textState,
            modifier = Modifier.weight(1.0f),
            style = YdsTheme.typography.body1
        )
        Spacer(Modifier.width(8.dp))

        state.rightIconState?.let { rightIconId ->
            YdsIcon(id = rightIconId)
            Spacer(Modifier.width(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    ListItem(
        onClick = {  },
        state = rememberListItemState(
            text = "로그아웃",
            isDisabled = true,
            leftIcon = R.drawable.ic_ground_filled
        )
    )
}