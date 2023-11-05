package com.yourssu.design.system.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "TopBar")
    }
}

@Preview(name = "TopBar")
@Composable
private fun PreviewTopBar() {
    TopBar()
}