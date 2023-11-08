package com.yourssu.design.system.compose.components

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.SearchTextField
import com.yourssu.design.system.compose.atom.TopBarButton
import com.yourssu.design.system.compose.base.YdsScaffold


@Composable
fun SearchTopBar(
    text: String = "",
    placeHolderText: String = "",
    isDisabled: Boolean = false,
    onValueChange: (String) -> Unit,
    onX: () -> Unit,
    onSearch: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = YdsTheme.colors.bgElevated,
        contentColor = YdsTheme.colors.textPrimary,
        elevation = 0.dp,
        contentPadding = PaddingValues(start = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                navigationIcon()
                SearchTextField(
                    text = text,
                    placeHolderText = placeHolderText,
                    isDisabled = isDisabled,
                    onValueChange = onValueChange,
                    onX = onX,
                    onSearch = onSearch,
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 16.dp)
                        .align(alignment = CenterVertically)
                )
            }
        }
    }
}

@Preview(name = "SearchTopBar")
@Composable
private fun PreviewSearchTopBar() {
    val context = LocalContext.current
    var text: String by rememberSaveable { mutableStateOf("") }

    YdsTheme {
        YdsScaffold(
            topBar = {
                SearchTopBar(
                    text = text,
                    placeHolderText = "Enabled",
                    isDisabled = false,
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
                    navigationIcon = {
                        TopBarButton(
                            icon = R.drawable.ic_arrow_left_line,
                            isDisabled = false,
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "navigationIcon Clicked!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                )
            }
        ) {

        }
    }
}