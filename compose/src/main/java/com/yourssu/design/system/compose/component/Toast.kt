package com.yourssu.design.system.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourssu.design.system.compose.base.MyScaffold
import kotlinx.coroutines.launch

@Composable
fun Toast(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "Toast")
    }
}

@Preview(name = "Toast")
@Composable
private fun PreviewToast() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    MyScaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Snackbar")
                }
            }
        ) {
            Text(text = "Show Snackbar")
        }
    }
}