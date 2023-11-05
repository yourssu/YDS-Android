package com.yourssu.design.system.compose.atom

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.LocalTextStyle
import com.yourssu.design.system.compose.base.YdsBaseButton
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.foundation.LocalYdsTypography
import com.yourssu.design.system.compose.foundation.White000
import com.yourssu.design.system.compose.foundation.YdsTextStyle
import com.yourssu.design.system.compose.foundation.YdsTypography
import com.yourssu.design.system.compose.states.ButtonColorState


private enum class TextOrIconState {
    Text,
    Icon
}

@Composable
fun TopBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = true,
    text: String = "",
    @DrawableRes icon: Int? = null,
) {

    val textOrIcon = if (icon == null && text.isNotEmpty()) {
        TextOrIconState.Text
    } else if (icon != null && text.isEmpty()) {
        TextOrIconState.Icon
    } else {
        TextOrIconState.Text
    }

//    } else { // icon != null && text.isNotEmpty() /// icon == null && text.isEmpty()
//          이런 경우가 발생할 가능성? => 일단은 text 모드로
//    }

    YdsBaseButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonColorState(
            contentColor = YdsTheme.colors.buttonNormal,
            disabledContentColor = YdsTheme.colors.buttonDisabled
        ),
        enabled = !isDisabled,
        rounding = 0.dp,
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        when (textOrIcon) {
            TextOrIconState.Text -> {
                YdsText(text = text, style = YdsTheme.typography.button0)
            }

            TextOrIconState.Icon -> {
                if (icon != null) {
                    Icon(
                        id = icon
                    )
                }
            }
        }
    }

}


@Preview(
    name = "TopBarButton",
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun PreviewTopBarButton() {

    val context = LocalContext.current

    fun onClick() {
        Toast.makeText(
            context,
            "Click!",
            Toast.LENGTH_SHORT
        ).show()
    }

    YdsTheme {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    icon = R.drawable.ic_ground_filled,
                    isDisabled = false
                )
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    icon = R.drawable.ic_ground_filled,
                    isDisabled = true
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    text = "닫기",
                    isDisabled = false
                )
                TopBarButton(
                    onClick = {
                        onClick()
                    },
                    text = "닫기",
                    isDisabled = true
                )
            }
        }
    }
}