package com.yourssu.design.system.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchTopBar(
    onValueChange: (String) -> Unit,
    onX: () -> Unit,
    onSearch: () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = YdsTheme.colors.bgElevated,
        contentColor = YdsTheme.colors.textPrimary,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                navigationIcon()
                SearchTextField(
                    searchTextFieldState = searchTextFieldState,
                    onValueChange = onValueChange,
                    onX = onX
                ) {
                    onSearch()
                }
            }
        }
    }
}

@Preview(name = "SearchTopBar")
@Composable
private fun PreviewSearchTopBar() {
    SearchTopBar()
}