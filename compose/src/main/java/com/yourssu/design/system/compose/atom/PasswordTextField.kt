package com.yourssu.design.system.compose.atom

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.YdsText

@Composable
fun PasswordTextField(
    text: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    onValueChange: (value: String) -> Unit,
    onErrorChange: (Boolean) -> Unit,
    placeHolder: String = "",
    hintText: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    var showPassword by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = YdsTheme.colors.inputFieldElevated,
                cursorColor = YdsTheme.colors.textPointed,
                errorBorderColor = YdsTheme.colors.textWarned,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = YdsTheme.colors.textPointed,
                disabledTextColor = YdsTheme.colors.textDisabled,
                disabledBorderColor = Color.Transparent,
                textColor = YdsTheme.colors.textSecondary,
            ),
            isError = isError,
            enabled = isEnabled,
            placeholder = {
                YdsText(
                    text = placeHolder,
                    style = YdsTheme.typography.body1,
                    color = YdsTheme.colors.textTertiary,
                )
            },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                if (showPassword) {
                    IconButton(
                        onClick = { showPassword = false },
                        modifier = Modifier.indication(interactionSource, null),
                    ) {
                        Icon(
                            id = R.drawable.ic_eyeclosed_line,
                            iconSize = IconSize.Medium,
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true },
                        modifier = Modifier.indication(interactionSource, null),
                    ) {
                        Icon(
                            id = R.drawable.ic_eyeopen_line,
                            iconSize = IconSize.Medium,
                        )
                    }
                }
            },
            textStyle = YdsTheme.typography.body1.toTextStyle(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
        )
        if (hintText.isNotEmpty()) {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Spacer(
                    modifier = Modifier
                        .width(16.dp),
                )
                YdsText(
                    text = hintText,
                    style = YdsTheme.typography.caption1,
                    color = if (isError) {
                        YdsTheme.colors.textWarned
                    } else if (!isEnabled) {
                        YdsTheme.colors.textDisabled
                    } else {
                        YdsTheme.colors.textTertiary
                    },
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewPasswordTextField() {
    var isError by remember { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    Column {
        PasswordTextField(
            text = text,
            isError = isError, isEnabled = true,
            placeHolder = "플레이스 홀더",
            onValueChange = { value ->
                isError = value.equals("x")
                text = value
            },
            hintText = "힌트 텍스트",
            modifier = Modifier.padding(10.dp),
            onErrorChange = { isError = it },
        )

        var text2 by rememberSaveable { mutableStateOf("") }
        var text3 by rememberSaveable { mutableStateOf("") }

        PasswordTextField(
            text = text2,
            isEnabled = false,
            onValueChange = { value ->
                text2 = value
            },
            modifier = Modifier.padding(bottom = 10.dp),
            hintText = "힌트 텍스트",
            onErrorChange = { isError = it },
        )

        PasswordTextField(
            text = text3,
            isError = true,
            onValueChange = { value -> text3 = value },
            onErrorChange = { isError = it },
        )

    }
}