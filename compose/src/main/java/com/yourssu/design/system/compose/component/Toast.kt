package com.yourssu.design.system.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.YdsText
import com.yourssu.design.system.compose.foundation.ToastData
import com.yourssu.design.system.compose.foundation.ToastDuration
import kotlin.math.max

@Composable
fun Toast(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.toastBG,
    contentColor: Color = YdsTheme.colors.textBright,
    toastData: ToastData
) {
    Layout(
        content = {
            Box(
                modifier = modifier
                    .padding(horizontal = ToastHorizontalMargin)
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .padding(
                        horizontal = ToastHorizontalPadding,
                        vertical = ToastVerticalPadding
                    )
                    .fillMaxWidth()
            ) {
                YdsText(
                    text = toastData.message,
                    color = contentColor,
                    style = YdsTheme.typography.body2,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }) { measurables, constraints ->
        require(measurables.size == 1) {
            "text for Snackbar expected to have exactly only one child"
        }
        val textPlaceable = measurables.first().measure(constraints)
        val firstBaseline = textPlaceable[FirstBaseline]
        val lastBaseline = textPlaceable[LastBaseline]
        require(firstBaseline != AlignmentLine.Unspecified) { "No baselines for text" }
        require(lastBaseline != AlignmentLine.Unspecified) { "No baselines for text" }

        val minHeight =
            if (firstBaseline == lastBaseline) {
                48.dp
            } else {
                68.dp
            }
        val containerHeight = max(minHeight.roundToPx(), textPlaceable.height)
        layout(constraints.maxWidth, containerHeight) {
            val textPlaceY = (containerHeight - textPlaceable.height) / 2
            textPlaceable.placeRelative(0, textPlaceY)
        }
    }
}

private val ToastHorizontalPadding = 24.dp
private val ToastVerticalPadding = 16.dp
private val ToastHorizontalMargin = 8.dp


@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun ToastPreview() {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Toast(toastData = object : ToastData {
            override val message: String
                get() = "한 줄일 때는 중앙 정렬을 해주세요."
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        Toast(toastData = object : ToastData {
            override val message: String
                get() = "줄 수가 두 줄 이상이 되는 토스트 메시지입니다. 좌측 정렬을 해주세요"
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        Toast(toastData = object : ToastData {
            override val message: String
                get() = "잠, 아스라히 노새, 멀듯이, 흙으로 봅니다."
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        Toast(toastData = object : ToastData {
            override val message: String
                get() = "잠, 아스라히 노새, 멀듯이, 흙으로 봅니다. 때 불러 가슴속에 나의 풀이 이름과 언덕 오면 가을 봅니다."
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        Toast(toastData = object : ToastData {
            override val message: String
                get() = "잠, 아스라히 노새, 멀듯이, 흙으로 봅니다. 때 불러 가슴속에 나의 풀이 이름과 언덕 오면 가을 봅니다.\n잠, 아스라히 노새, 멀듯이, 흙으로 봅니다. 때 불러 가슴속에 나의 풀이 이름과 언덕 오면 가을 봅니다."
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
    }
}