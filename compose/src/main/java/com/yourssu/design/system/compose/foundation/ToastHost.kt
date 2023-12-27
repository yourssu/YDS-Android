package com.yourssu.design.system.compose.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yourssu.design.system.compose.component.Toast
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume



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


/**
 *  Toast를 보여주기 위해선 ToastHost를 사용해야 합니다.
 * ex) toastHostState.showToast(
 *                    message = "Snackbar",
 *                    duration = ToastDuration.SHORT
 *                  )
 */
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
                ToastDuration.SHORT -> delay(ToastDurationShort)
                ToastDuration.LONG -> delay(ToastDurationLong)
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

private const val ToastDurationShort = 1500L
private const val ToastDurationLong = 3000L