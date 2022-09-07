package com.yourssu.composedesignsystem.ui.theme.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yourssu.composedesignsystem.ui.theme.YdsTheme
import com.yourssu.composedesignsystem.ui.theme.rule.YdsBorder

enum class Direction {
    Horizontal, Vertical
}

enum class Thickness(val value: Dp) {
    Thin(YdsBorder.thin),
    Thick(YdsBorder.thick)
}

@Composable
fun Divider(
    direction: Direction,
    thickness: Thickness,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                color = when (thickness) {
                    Thickness.Thin -> YdsTheme.colors.borderNormal
                    Thickness.Thick -> YdsTheme.colors.borderThin
                }
            )
            .composed {
                when (direction) {
                    Direction.Horizontal -> Modifier
                        .fillMaxWidth()
                        .height(thickness.value)
                    Direction.Vertical -> Modifier
                        .fillMaxHeight()
                        .width(thickness.value)
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun DividerPreview() {
    YdsTheme {
        Column(modifier = Modifier.height(IntrinsicSize.Min)) {
            Text(text = "one")
            Text(text = "two")
            Divider(
                direction = Direction.Horizontal,
                thickness = Thickness.Thick
            )
            Text(text = "three")

            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Text(text = "abcd")
                Divider(
                    direction = Direction.Vertical,
                    thickness = Thickness.Thin
                )
                Text(text = "efgh")
            }
        }
    }
}