package com.yourssu.design.system.compose.component.toast

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.yourssu.design.system.compose.rule.Duration
import com.yourssu.design.system.compose.rule.YdsEasing

data class ToastTransitionItem(
    val toastData: ToastData?,
    val opacityTransition: OpacityTransition
)

typealias OpacityTransition = @Composable (toast: @Composable () -> Unit) -> Unit

/**
 * 토스트 애니메이션 FadeInFadeOut
 *
 * 작동 이해를 위해 주석을 남겨둘 예정입니다.
 *
 * @param newToastData 새로운 토스트 데이터 입력. 입력 없다면 null
 * @param modifier Modifier
 * @param toast 토스트 컴포저블
 */
@Composable
fun FadeInFadeOut(
    newToastData: ToastData?,
    modifier: Modifier = Modifier,
    toast: @Composable (ToastData) -> Unit // Composable of Toast
) {
    // 앞으로 나타나기로 예정된 토스트의 정보
    var scheduledToastData by remember { mutableStateOf<ToastData?>(null) }
    // 현재 나타나고 있는 토스트의 정보와 Opacity Composable
    val toastTransitions = remember { mutableListOf<ToastTransitionItem>() }
    var scope by remember { mutableStateOf<RecomposeScope?>(null) }

    if (newToastData != scheduledToastData) {
        // 새로 발생한 토스트 != 앞으로 나타나기로 예정된 토스트의 정보 => 새 Toast 발생
        scheduledToastData = newToastData // 앞으로 나타날 토스트에 새로운 토스트로 업데이트
        val toastDataList = toastTransitions.map {
            it.toastData
        }.toMutableList() // 현재 나타난 토스트의 정보로 초기화
        toastDataList.add(newToastData) // 새로 발생한 토스트 추가
        toastTransitions.clear() // 현재 나타난 토스트 비우기 (새로운 토스트가 발생했으므로)
        toastDataList
            .filterNotNull()
            .mapTo(destination = toastTransitions) { appearedToastData ->
                ToastTransitionItem(appearedToastData) { toast ->
                    val isVisible = appearedToastData == newToastData
                    val opacity = animatedOpacity(
                        animation = tween(
                            easing = YdsEasing,
                            easing = YdsInAndOutEasing,
                            delayMillis = 0,
                            durationMillis = Duration.Medium.millis
                        ),
                        visible = isVisible,
                        onAnimationFinish = { // 토스트가 사라질때 발동
                            if (appearedToastData != scheduledToastData) {
                                toastTransitions.removeAll { it.toastData == appearedToastData }
                                // 발생한 토스트를 제거
                                scope?.invalidate()
                            }
                        }
                    )
                    Box( // 투명도 그래픽 레이어를 만들어주는 Toast 상위 Composable
                        Modifier
                            .graphicsLayer(
                                alpha = opacity.value
                            )
                    ) {
                        toast()
                    }
                }
            }
    }

    Box(modifier) {
        scope = currentRecomposeScope
        toastTransitions.forEach { (toastData, opacity) ->
            key(toastData) {
                opacity {
                    toast(toastData!!)
                }
            }
        }
    }
}

/**
 * 불투명도 애니메이션을 위한 함수
 * @param animationSpec 애니메이션 스펙 ex) tween
 * @param visible 보여줄지 말지
 * @param onAnimationFinish 애니메이션이 끝난 후 실행할 함수
 */
@Composable
fun animatedOpacity(
    animation: AnimationSpec<Float>,
    visible: Boolean,
    onAnimationFinish: () -> Unit = {}
): State<Float> {
    val alpha = remember { Animatable(if (!visible) 1f else 0f) }
    LaunchedEffect(visible) {
        alpha.animateTo(
            if (visible) 1f else 0f,
            animationSpec = animation
        )
        onAnimationFinish()
    }
    return alpha.asState()
}

