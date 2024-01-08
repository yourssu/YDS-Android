package com.yourssu.design.system.compose.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.base.YdsBaseButton
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.states.ButtonColorState
import com.yourssu.design.system.compose.states.ButtonSizeState

enum class PlainButtonSize {
    Large, Medium, Small
}

@Composable
private fun plainButtonSizeStateBySize(
    size: PlainButtonSize
): ButtonSizeState = when (size) {
    PlainButtonSize.Large -> ButtonSizeState(iconSize = IconSize.Medium)
    PlainButtonSize.Medium -> ButtonSizeState(
        typo = YdsTheme.typography.button3,
        iconSize = IconSize.Small
    )
    PlainButtonSize.Small -> ButtonSizeState(
        typo = YdsTheme.typography.button4,
        iconSize = IconSize.ExtraSmall
    )
}

@Composable
private fun plainButtonColor(
    isWarned: Boolean,
    isPointed: Boolean
) = ButtonColorState(
    contentColor = when {
        isWarned -> YdsTheme.colors.buttonWarned
        isPointed -> YdsTheme.colors.buttonPoint
        else -> YdsTheme.colors.buttonNormal
    },
    disabledContentColor = YdsTheme.colors.buttonDisabled
)

@Composable
fun PlainButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    isDisabled: Boolean = false,
    isWarned: Boolean = false,
    isPointed: Boolean = false,
    sizeType: PlainButtonSize = PlainButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val sizeState = plainButtonSizeStateBySize(size = sizeType)
    val typo = sizeState.typo
    val iconSize = sizeState.iconSize

    YdsBaseButton(
        onClick = onClick,
        modifier = modifier,
        enabled = !isDisabled,
        colors = plainButtonColor(isWarned = isWarned, isPointed = isPointed),
        interactionSource = interactionSource,
        contentPadding = PaddingValues(0.dp)
    ) {
        if (sizeType == PlainButtonSize.Large) {
            // sizeType이 Large일 때는 아이콘만
            val iconRes = leftIcon ?: rightIcon

            require(iconRes != null) { "Large 버튼은 아이콘이 지정되어야 합니다." }
            Icon(
                id = iconRes,
                iconSize = iconSize
            )
        } else {
            leftIcon?.let { icon ->
                Icon(
                    id = icon,
                    iconSize = iconSize
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            YdsText(
                text = text,
                style = typo
            )

            if (leftIcon == null) {
                rightIcon?.let { icon ->
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        id = icon,
                        iconSize = iconSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlainButtonPreview() {

    var sizeState by rememberSaveable { mutableStateOf(PlainButtonSize.Large) }
    var pointed by remember { mutableStateOf(false) }

    YdsTheme {
        PlainButton(
            onClick = {
                sizeState = PlainButtonSize.Medium
                pointed = true
            },
            leftIcon = R.drawable.ic_ground_filled,
            sizeType = sizeState,
            text = "Large",
            isPointed = pointed
        )
    }
}