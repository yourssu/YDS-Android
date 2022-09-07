package com.yourssu.composedesignsystem.ui.theme.atom

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

    private val isPressed: Boolean
        @Composable get() = interactionSource.collectIsPressedAsState().value

    val backgroundColor: Color
        @Composable get() = if (isPressed && !isDisabledState) {
            YdsTheme.colors.bgPressed
        } else {
            YdsTheme.colors.bgNormal
        }

    companion object {
        private const val textKey = "text"
        private const val leftIconKey = "leftIcon"
        private const val rightIconKey = "rightIcon"
        private const val disabledKey = "disabled"

        val Saver = run {
            mapSaver(
                save = {
                    mapOf(
                        textKey to it.textState,
                        leftIconKey to it.leftIconState,
                        rightIconKey to it.rightIconState,
                        disabledKey to it.isDisabledState
                    )
                },
                restore = {
                    ListItemState(
                        it[textKey] as String,
                        it[leftIconKey] as Int?,
                        it[rightIconKey] as Int?,
                        it[disabledKey] as Boolean
                    )
                }
            )
        }
    }
}

@Composable
fun rememberListItemState(
    text: String,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): ListItemState = rememberSaveable(
    text, leftIcon, rightIcon, isDisabled, interactionSource,
    saver = ListItemState.Saver
) {
    ListItemState(text, leftIcon, rightIcon, isDisabled, interactionSource)
}

@Composable
fun ListItem(
    onClick: () -> Unit,
    state: ListItemState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(48.dp)
            .noRippleClickable(interactionSource = state.interactionSource) {
                if (!state.isDisabledState) {
                    onClick()
                }
            }
            .background(color = state.backgroundColor),
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