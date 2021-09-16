package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.dpToPx
import kotlin.math.abs

class Toggle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CompoundButton(context, attrs) {

    init {
        setToggleInfo()
    }

    var isDisabled: Boolean = false
        set(isDisabled) {
            field = isDisabled
            setToggleInfo()
        }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        selectedListener?.onSelected(selected)
        setToggleInfo()
    }

    interface SelectedListener {
        fun onSelected(boolean: Boolean)
    }

    private var selectedListener: SelectedListener? = null

    fun setOnSelectedListener(listener: SelectedListener) {
        selectedListener = listener
    }

    private var isTogglePressed = false
    private var startX = 0f
    private var startY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isDisabled) {
                    isTogglePressed = true
                    startX = event.x
                    startY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                // 처음 시작한 좌표 대비 토글의 크기 이상 벗어나면 터치 취소
                if (abs(event.x - startX) > context.dpToPx(WIDTH)
                    || abs(event.y - startY) > context.dpToPx(HEIGHT)
                ) {
                    isTogglePressed = false
                }

                if (isTogglePressed && !isDisabled) {
                    isSelected = !isSelected
                    setToggleInfo()
                }
            }
        }

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = context.dpToIntPx(WIDTH)
        val height = context.dpToIntPx(HEIGHT)
        setMeasuredDimension(width, height)
    }

    private fun setToggleInfo() {
        if (isSelected && isDisabled) {
            setButtonDrawable(R.drawable.toggle_selected_disabled)
        } else if (isSelected && !isDisabled) {
            setButtonDrawable(R.drawable.toggle_selected_enabled)
        } else if (!isSelected && isDisabled) {
            setButtonDrawable(R.drawable.toggle_unselected_disabled)
        } else {
            setButtonDrawable(R.drawable.toggle_unselected_enabled)
        }
    }

    companion object {

        private const val WIDTH = 51f
        private const val HEIGHT = 31f

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(toggle: Toggle, isDisabled: Boolean) {
            toggle.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("isSelected")
        fun setSelected(toggle: Toggle, isSelected: Boolean) {
            toggle.isSelected = isSelected
        }

        @JvmStatic
        @BindingAdapter("selectedListener")
        fun setSelectedListener(toggle: Toggle, listener: SelectedListener) {
            toggle.setOnSelectedListener(listener)
        }
    }


}