package com.yourssu.design.system.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yourssu.design.system.compose.YdsTheme

val LocalIsImpactFeedbackEnabled = compositionLocalOf { true }
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.bgElevated,
    contentColor: Color = YdsTheme.colors.bottomBarNormal,
    contentPadding: PaddingValues,
    isImpactFeedbackEnabled:Boolean = true,
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
    @DrawableRes icon: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

}