package com.yourssu.design.system.compose.foundation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import com.yourssu.design.system.compose.rule.YdsInAndOutEasing

data class ToastAnimationItem(
    val toastData: ToastData?,
    val opacityTransition: opacityTransition
)

fun Any?.helpCode() = System.identityHashCode(this) % 1000

typealias opacityTransition = @Composable (toast: @Composable () -> Unit) -> Unit

/**
 * 토스트 애니메이션 FadeInFadeOut
 *
 * 구현과정 이해를 위해 주석을 남겨둘 예정입니다.
 *
 * @param newToastData 새로운 토스트 데이터 입력. 입력 없다면 null
 * @param modifier Modifier
 * @param toast 토스트 컴포저블
 */
@Composable
fun FadeInFadeOutWithScale(
    newToastData: ToastData?,
    modifier: Modifier = Modifier,
    toast: @Composable (ToastData) -> Unit // Composable of Toast
) {
    var scheduledToastData by remember { mutableStateOf<ToastData?>(null) }
    val toastAnimations = remember { mutableListOf<ToastAnimationItem>() }
    var scope by remember { mutableStateOf<RecomposeScope?>(null) }

    if (newToastData != scheduledToastData) {
        scheduledToastData = newToastData
        val toastDataList = toastAnimations.map {
            it.toastData
        }.toMutableList()
        toastDataList.add(newToastData)
        toastAnimations.clear()
        toastDataList
            .filterNotNull()
            .mapTo(destination = toastAnimations) { appearedToastData ->
                ToastAnimationItem(appearedToastData) { toast ->
                    val isVisible = appearedToastData == newToastData
                    val opacity = animatedOpacity(
                        animation = tween(
                            easing = YdsInAndOutEasing,
                            delayMillis = 0,
                            durationMillis = Duration.Medium.millis
                        ),
                        visible = isVisible,
                        onAnimationFinish = {
                            if (appearedToastData != scheduledToastData) {
                                toastAnimations.removeAll { it.toastData == appearedToastData }
                                scope?.invalidate()
                            }
                        }
                    )
                    Box(
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
        toastAnimations.forEach { (toastData, opacity) ->
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

