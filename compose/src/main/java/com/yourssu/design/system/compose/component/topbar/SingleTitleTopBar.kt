package com.yourssu.design.system.compose.component.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.atom.TopBarButton
import com.yourssu.design.system.compose.base.YdsScaffold
import com.yourssu.design.system.compose.base.YdsText

@Composable
fun SingleTitleTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        contentPadding = PaddingValues(end = 4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row {
                navigationIcon()
                YdsText(
                    modifier = Modifier
                        .padding(16.dp, 17.dp, 16.dp, 8.dp)
                        .wrapContentHeight(),
                    text = title,
                    style = YdsTheme.typography.title2,
                    color = YdsTheme.colors.textPrimary,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(0.dp)
                    .align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        }
    }
}

@Preview(name = "SingleTitleTopBar")
@Composable
private fun PreviewSingleTitleTopBar() {
    YdsTheme {
        YdsScaffold(
            topBar = {
                SingleTitleTopBar(
                    title = "타이틀",
                    navigationIcon = {
                        TopBarButton(
                            icon = R.drawable.ic_arrow_left_line,
                        )
                    },
                    actions = {
                        TopBarButton(
                            icon = R.drawable.ic_ground_filled,
                        )
                        TopBarButton(
                            icon = R.drawable.ic_ground_filled,
                        )
                        TopBarButton(
                            icon = R.drawable.ic_ground_filled,
                        )
                    },
                )
            }
        ) {

        }
    }
}