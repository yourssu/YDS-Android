package com.yourssu.design.system.compose.component.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourssu.design.system.compose.YdsTheme
import com.yourssu.design.system.compose.base.YdsText

@Composable
fun Toast(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YdsTheme.colors.toastBG,
    contentColor: Color = YdsTheme.colors.textBright,
    toastData: ToastData,
) {
    var lineCount by remember { mutableStateOf(1) }
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
            onTextLayout = {
                lineCount = it.lineCount
            },
            modifier = Modifier.align(
                if (lineCount == 1) {
                    Alignment.Center
                } else {
                    Alignment.CenterStart
                }
            )
        )
    }
}

private val ToastHorizontalPadding = 24.dp
private val ToastVerticalPadding = 16.dp
private val ToastHorizontalMargin = 8.dp


@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun ToastPreview() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
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
                get() = "잠, 아스라히 노새, 멀듯이, 흙으로 봅니다.".repeat(10)
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        Toast(toastData = object : ToastData {
            override val message: String
                get() = "계절이 지나가는 하늘에는\n" +
                        "가을로 가득 차 있습니다.\n" +
                        "\n" +
                        "나는 아무 걱정도 없이\n" +
                        "가을 속의 별들을 다 헤일 듯합니다.\n" +
                        "\n" +
                        "가슴속에 하나둘 새겨지는 별을\n" +
                        "이제 다 못 헤는 것은\n" +
                        "쉬이 아침이 오는 까닭이요,\n" +
                        "내일 밤이 남은 까닭이요,\n" +
                        "아직 나의 청춘이 다하지 않은 까닭입니다.\n" +
                        "\n" +
                        "별 하나에 추억과\n" +
                        "별 하나에 사랑과\n" +
                        "별 하나에 쓸쓸함과\n" +
                        "별 하나에 동경과\n" +
                        "별 하나에 시와\n" +
                        "별 하나에 어머니, 어머니,\n" +
                        "\n" +
                        "어머님, 나는 별 하나에 아름다운 말 한마디씩 불러 봅니다. 소학교 때 책상을 같이 했던 아이들의 이름과, 패, 경, 옥, 이런 이국 소녀들의 이름과, 벌써 아기 어머니 된 계집애들의 이름과, 가난한 이웃 사람들의 이름과, 비둘기, 강아지, 토끼, 노새, 노루, '프랑시스 잠[1]', '라이너 마리아 릴케[2]' 이런 시인의 이름을 불러 봅니다.\n" +
                        "\n" +
                        "이네들은 너무나 멀리 있습니다.\n" +
                        "별이 아스라이 멀듯이.\n" +
                        "\n" +
                        "어머님,\n" +
                        "그리고 당신은 멀리 북간도에 계십니다.\n" +
                        "\n" +
                        "나는 무엇인지 그리워\n" +
                        "이 많은 별빛이 내린 언덕 위에\n" +
                        "내 이름자를 써 보고\n" +
                        "흙으로 덮어 버리었습니다.\n" +
                        "\n" +
                        "딴은[3] 밤을 새워 우는 벌레는\n" +
                        "부끄러운 이름을 슬퍼하는 까닭입니다.\n" +
                        "\n" +
                        "그러나 겨울이 지나고 나의 별에도 봄이 오면\n" +
                        "무덤 위에 파란 잔디가 피어나듯이\n" +
                        "내 이름자 묻힌 언덕 위에도\n" +
                        "자랑처럼 풀이 무성할 거외다."
            override val duration: ToastDuration
                get() = ToastDuration.SHORT

            override fun dismiss() {
            }
        })
    }
}