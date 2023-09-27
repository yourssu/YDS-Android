package com.yourssu.design.system.compose.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.rule.YdsBorder

enum class Direction {
    Horizontal, Vertical
}

enum class Thickness(val value: Dp) {
    Thin(YdsBorder.Thin.dp),
    Thick(YdsBorder.Thick.dp)
}

@Composable
fun RowScope.Divider(
    thickness: Thickness,
    modifier: Modifier = Modifier,
) {
    Divider(
        direction = Direction.Vertical,
        thickness = thickness,
        modifier = modifier,
    )
}

@Composable
fun ColumnScope.Divider(
    thickness: Thickness,
    modifier: Modifier = Modifier,
) {
    Divider(
        direction = Direction.Horizontal,
        thickness = thickness,
        modifier = modifier,
    )
}

@Composable
fun Divider(
    direction: Direction,
    thickness: Thickness,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                color = when (thickness) {
                    Thickness.Thin -> YdsTheme.colors.borderNormal
                    Thickness.Thick -> YdsTheme.colors.borderThin
                },
            )
            .then(
                when (direction) {
                    Direction.Horizontal -> Modifier
                        .fillMaxWidth()
                        .height(thickness.value)

                    Direction.Vertical -> Modifier
                        .fillMaxHeight()
                        .width(thickness.value)
                },
            ),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun DividerPreview() {
    YdsTheme {
        Column(modifier = Modifier.height(IntrinsicSize.Min)) {
            YdsText(text = "one")
            YdsText(text = "two")
            Divider(thickness = Thickness.Thick)
            YdsText(text = "three")

            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                YdsText(
                    text = "abcd",
                    modifier = Modifier.padding(end = 8.dp),
                )
                Divider(thickness = Thickness.Thin)
                YdsText(
                    text = "efgh",
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}