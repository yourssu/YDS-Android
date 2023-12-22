package com.yourssu.design.system.compose.foundation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.yourssu.design.system.compose.component.Toast
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume


@Stable
class ToastHostState {
    private val mutex = Mutex()

    var currentToastData by mutableStateOf<ToastData?>(null)
        private set

    suspend fun showToast(
        message: String,
        duration: ToastDuration = ToastDuration.SHORT,
    ): ToastResult = mutex.withLock {
        try {
            return suspendCancellableCoroutine { continuation ->
                currentToastData = ToastDataImpl(message, duration, continuation)
            }
        } finally {
            currentToastData = null
        }
    }

    @Stable
    private class ToastDataImpl(
        override val message: String,
        override val duration: ToastDuration,
        private val continuation: CancellableContinuation<ToastResult>
    ) : ToastData {

        override fun dismiss() {
            if (continuation.isActive) {
                continuation.resume(ToastResult.Dismissed)
            }
        }
    }
}

@Composable
fun ToastHost(
    toastHostState: ToastHostState,
    modifier: Modifier = Modifier,
    toast: @Composable (ToastData) -> Unit = { Toast(toastData = it) }
) {
    val currentToastData = toastHostState.currentToastData
    LaunchedEffect(currentToastData) {
        if (currentToastData != null) {
            when (currentToastData.duration) {
                ToastDuration.SHORT -> delay(1500)
                ToastDuration.LONG -> delay(3000)
            }
            currentToastData.dismiss()
        }
    }
    FadeInFadeOutWithScale(
        current = toastHostState.currentToastData,
        modifier = modifier,
        content = toast
    )
}


interface ToastData {
    val message: String
    val duration: ToastDuration

    fun dismiss()
}

enum class ToastResult {
    Dismissed,
}

enum class ToastDuration {
    SHORT, LONG,
}


@Composable
private fun FadeInFadeOutWithScale(
    current: ToastData?,
    modifier: Modifier = Modifier,
    content: @Composable (ToastData) -> Unit
) {
    val state = remember { FadeInFadeOutState<ToastData?>() }
    if (current != state.current) {
        state.current = current
        val keys = state.items.map { it.key }.toMutableList()
        if (!keys.contains(current)) {
            keys.add(current)
        }
        state.items.clear()
        keys.filterNotNull().mapTo(state.items) { key ->
            FadeInFadeOutAnimationItem(key) { children ->
                val isVisible = key == current
                val duration = if (isVisible) ToastFadeInMillis else ToastFadeOutMillis
                val opacity = animatedOpacity(
                    animation = tween(
                        easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f),
                        delayMillis = 0,
                        durationMillis = duration
                    ),
                    visible = isVisible,
                    onAnimationFinish = {
                        if (key != state.current) {
                            // leave only the current in the list
                            state.items.removeAll { it.key == key }
                            state.scope?.invalidate()
                        }
                    }
                )
                Box(
                    Modifier
                        .graphicsLayer(
                            alpha = opacity.value
                        )
                ) {
                    children()
                }
            }
        }
    }
    Box(modifier) {
        state.scope = currentRecomposeScope
        state.items.forEach { (item, opacity) ->
            key(item) {
                opacity {
                    content(item!!)
                }
            }
        }
    }
}

private class FadeInFadeOutState<T> {
    // we use Any here as something which will not be equals to the real initial value
    var current: Any? = Any()
    var items = mutableListOf<FadeInFadeOutAnimationItem<T>>()
    var scope: RecomposeScope? = null
}

private data class FadeInFadeOutAnimationItem<T>(
    val key: T,
    val transition: FadeInFadeOutTransition
)

private typealias FadeInFadeOutTransition = @Composable (content: @Composable () -> Unit) -> Unit

@Composable
private fun animatedOpacity(
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

@Composable
private fun animatedScale(animation: AnimationSpec<Float>, visible: Boolean): State<Float> {
    val scale = remember { Animatable(if (!visible) 1f else 0.8f) }
    LaunchedEffect(visible) {
        scale.animateTo(
            if (visible) 1f else 0.8f,
            animationSpec = animation
        )
    }
    return scale.asState()
}

private const val ToastFadeInMillis = 250
private const val ToastFadeOutMillis = 250