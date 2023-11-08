package com.yourssu.design.system.compose.atom

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentColor
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.IconSize
import com.yourssu.design.system.compose.base.YdsBaseButton
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.states.ButtonColorState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchTextField(
    text: String = "",
    placeHolderText: String = "",
    isDisabled: Boolean = false,
    onValueChange: (String) -> Unit,
    onX: () -> Unit,
    onSearch: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
) {
    val isFocused: Boolean = interactionSource.collectIsFocusedAsState().value
    val isTyping: Boolean = isFocused and text.isNotBlank()

    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        textStyle = YdsTheme.typography.body1.toTextStyle(),
        enabled = !isDisabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
        cursorBrush = SolidColor(YdsTheme.colors.textPointed),
        modifier = Modifier
            .width(350.dp)
            .height(40.dp)
            .then(modifier)
            .background(
                color = YdsTheme.colors.inputFieldElevated,
                shape = RoundedCornerShape(8.dp)
            ),
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = text,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    YdsText(
                        text = placeHolderText,
                        style = YdsTheme.typography.body1,
                        color = YdsTheme.colors.textTertiary
                    )
                },
                leadingIcon = {
                    Icon(
                        id = R.drawable.ic_search_line,
                        iconSize = IconSize.Small,
                        tint = if (isTyping) {
                            YdsTheme.colors.textSecondary
                        } else {
                            LocalContentColor.current
                        }
                    )
                },
                trailingIcon = {
                    if (isTyping) {
                        YdsBaseButton(onClick = { onX() }, colors = ButtonColorState()) {
                            Icon(
                                id = R.drawable.ic_x_line,
                                iconSize = IconSize.ExtraSmall,
                                tint = YdsTheme.colors.buttonNormal,
                            )
                        }
                    }
                },
                singleLine = true,
                enabled = !isDisabled,
                isError = false,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = YdsTheme.colors.textSecondary,
                    disabledTextColor = YdsTheme.colors.textDisabled,
                    backgroundColor = YdsTheme.colors.inputFieldElevated,
                    cursorColor = YdsTheme.colors.textPointed,
                    errorCursorColor = YdsTheme.colors.textPointed,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = YdsTheme.colors.textWarned,
                    leadingIconColor = YdsTheme.colors.textTertiary,
                    disabledLeadingIconColor = YdsTheme.colors.textDisabled,
                    errorLeadingIconColor = Color.Transparent,
                    trailingIconColor = YdsTheme.colors.textTertiary,
                    disabledTrailingIconColor = YdsTheme.colors.textDisabled,
                    errorTrailingIconColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    disabledLabelColor = Color.Transparent,
                    errorLabelColor = Color.Transparent,
                    placeholderColor = YdsTheme.colors.textTertiary,
                    disabledPlaceholderColor = YdsTheme.colors.textDisabled
                )
            )
        }
    )
}

@Preview(name = "ExSearchTextField")
@Composable
private fun PreviewSearchTextField() {
    val context = LocalContext.current
    var text: String by rememberSaveable { mutableStateOf("") }

    SearchTextField(
        onValueChange = {
            text = it
        },
        onX = {
            Toast.makeText(
                context,
                "Erase!",
                Toast.LENGTH_SHORT
            ).show()
            text = ""
        },
        onSearch = {
            Toast.makeText(
                context,
                "onSearch!",
                Toast.LENGTH_SHORT
            ).show()
        },
        text = text,
        placeHolderText = "플레이스 홀더 입니다",
        isDisabled = false,
    )
}