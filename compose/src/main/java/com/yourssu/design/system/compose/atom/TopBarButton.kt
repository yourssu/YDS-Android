package com.yourssu.design.system.compose.atom

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.YdsBaseButton
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.states.ButtonColorState

@Composable
fun TopBarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isDisabled: Boolean = false,
    text: String = "",
    @DrawableRes icon: Int? = null,
) {
    YdsBaseButton(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        colors = ButtonColorState(
            contentColor = YdsTheme.colors.buttonNormal,
            disabledContentColor = YdsTheme.colors.buttonDisabled,
        ),
        enabled = !isDisabled,
        rounding = 0.dp,
        minWidth = 0.dp,
        minHeight = 0.dp,
        contentPadding = PaddingValues(horizontal = 12.dp),
    ) {
        if (icon == null && text.isNotEmpty()) {
            YdsText(
                text = text,
                style = YdsTheme.typography.button0,
            )
        } else {
            if (icon != null) {
                Icon(
                    id = icon,
                )
            }
        }
    }
}


@Preview(
    name = "TopBarButton",
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
private fun PreviewTopBarButton() {

    val context = LocalContext.current

    fun onClick() {
        Toast.makeText(
            context,
            "Click!",
            Toast.LENGTH_SHORT,
        ).show()
    }

    YdsTheme {
        Row(
            modifier = Modifier.height(56.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.height(56.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    icon = R.drawable.ic_ground_filled,
                    isDisabled = false,
                )
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    icon = R.drawable.ic_ground_filled,
                    isDisabled = true,
                )
            }
            Column(
                modifier = Modifier.height(56.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    text = "닫기",
                    isDisabled = false,
                )
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    text = "닫기",
                    isDisabled = true,
                )
            }
        }
    }
}