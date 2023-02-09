package com.yourssu.design.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.R
import com.yourssu.design.compose.YdsTheme
import com.yourssu.design.compose.foundation.YdsIcon
import com.yourssu.design.compose.base.noRippleClickable

@Composable
fun ListItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed && !isDisabled) {
        YdsTheme.colors.bgPressed
    } else {
        Color.Transparent
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .noRippleClickable(interactionSource) {
                if (!isDisabled) {
                    onClick()
                }
            }
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))

        leftIcon?.let { icon ->
            YdsIcon(id = icon, tint = YdsTheme.colors.buttonNormal)
            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = text,
            modifier = Modifier.weight(1.0f),
            style = YdsTheme.typography.body1,
            color = YdsTheme.colors.textSecondary
        )

        rightIcon?.let { icon ->
            Spacer(Modifier.width(8.dp))
            YdsIcon(id = icon, tint = YdsTheme.colors.buttonNormal)
        }

        Spacer(Modifier.width(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {

    YdsTheme {
        Column {
            ListItem(text = "로그아웃", onClick = {}, rightIcon = R.drawable.ic_ground_filled)
            ListItem(text = "로그아웃", onClick = {}, rightIcon = R.drawable.ic_ground_filled, isDisabled = true)

        }
    }
}