package com.yourssu.design.system.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.R
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.Icon
import com.yourssu.design.system.compose.base.noRippleClickable

val LocalIsImpactFeedbackEnabled = compositionLocalOf { true }

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    contentPadding: PaddingValues,
    isImpactFeedbackEnabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        contentPadding = contentPadding
    ) {
        CompositionLocalProvider(LocalIsImpactFeedbackEnabled provides isImpactFeedbackEnabled) {
            content()
        }
    }
}

@Composable
fun RowScope.BottomBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isImpactFeedbackEnabled = LocalIsImpactFeedbackEnabled.current
    val haptic = LocalHapticFeedback.current

    Icon(
        id = icon,
        modifier = modifier.noRippleClickable(
            interactionSource = interactionSource
        ) {
            if (isImpactFeedbackEnabled) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            onClick()
        },
    )
}

@Preview
@Composable
fun PreviewBottomBar() {
    YdsTheme {
        BottomBar(
            contentPadding = PaddingValues(0.dp),
        ) {
            BottomBarButton(
                onClick = {},
                icon = R.drawable.ic_ground_filled
            )
            BottomBarButton(
                onClick = {},
                icon = R.drawable.ic_ground_filled
            )
            BottomBarButton(
                onClick = {},
                icon = R.drawable.ic_ground_filled
            )
            BottomBarButton(
                onClick = {},
                icon = R.drawable.ic_ground_filled
            )
            BottomBarButton(
                onClick = {},
                icon = R.drawable.ic_ground_filled
            )
        }
    }
}

@Preview
@Composable
fun PreviewMaterialBottomBar() {
    YdsTheme {
        androidx.compose.material.BottomAppBar {
            Button(onClick = { /*TODO*/ }) {
                
            }
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
        }
    }
}