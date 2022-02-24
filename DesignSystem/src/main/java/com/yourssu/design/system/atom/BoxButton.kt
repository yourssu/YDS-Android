package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutBoxButtonBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.dpToPx

@SuppressLint("ViewConstructor")
class BoxButton : LinearLayout {
    constructor(context: Context) : super(context) {
        setBoxButtonInfo()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setBoxButtonInfo()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setBoxButtonInfo()
    }

    private val binding: LayoutBoxButtonBinding by lazy {
        LayoutBoxButtonBinding.inflate(
            LayoutInflater.from(
                context
            ), this, true
        )
    }

    var type: Int = FILLED
        set(type) {
            field = type
            setBoxButtonInfo()
        }

    var size: Int = ExtraLarge
        set(size) {
            field = size
            setBoxButtonInfo()
        }

    var rounding: Int = 4
        set(rounding) {
            field = rounding
            setBoxButtonInfo()
        }

    var isDisabled: Boolean = false
        set(isDisabled) {
            field = isDisabled
            setBoxButtonInfo()
        }

    var isWarned: Boolean = false
        set(isWarned) {
            field = isWarned
            setBoxButtonInfo()
        }

    var text: String = ""
        set(text) {
            field = text
            setBoxButtonInfo()
        }

    @Icon.Iconography
    var leftIcon: Int? = null
        set(leftIcon) {
            field = leftIcon
            setBoxButtonInfo()
        }

    @Icon.Iconography
    var rightIcon: Int? = null
        set(rightIcon) {
            field = rightIcon
            setBoxButtonInfo()
        }

    @ColorRes
    private var itemColor: Int = 0

    @ColorRes
    private var bgColor: Int = 0

    private fun setBoxButtonInfo() {
        setTheme()
        setIcon()
        setText()
        requestLayout()
        invalidate()
    }

    private fun setTheme() {
        setSize()

        when (type) {
            FILLED -> {
                setFilledTheme()
            }
            TINTED -> {
                setTintedTheme()
            }
            LINE -> {
                setLineTheme()
            }
        }
        setColorAndStroke()
    }

    private fun setFilledTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.buttonDisabled
                bgColor = R.color.buttonDisabledBG
            }
            isWarned -> {
                itemColor = R.color.buttonReversed
                bgColor = if (isPressed) R.color.buttonWarnedPressed else R.color.buttonWarned
            }
            else -> {
                itemColor = R.color.buttonReversed
                bgColor = if (isPressed) R.color.buttonPointPressed else R.color.buttonPoint
            }
        }
    }

    private fun setTintedTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.buttonDisabled
                bgColor = R.color.buttonDisabledBG
            }
            isWarned -> {
                itemColor = if (isPressed) R.color.buttonWarnedPressed else R.color.buttonWarned
                bgColor = R.color.buttonWarnedBG
            }
            else -> {
                itemColor = if (isPressed) R.color.buttonPointPressed else R.color.buttonPoint
                bgColor = R.color.buttonPointBG
            }
        }
    }

    private fun setLineTheme() {
        itemColor = when {
            isDisabled -> {
                R.color.buttonDisabled
            }
            isWarned -> {
                if (isPressed) R.color.buttonWarnedPressed else R.color.buttonWarned
            }
            else -> {
                if (isPressed) R.color.buttonPointPressed else R.color.buttonPoint
            }
        }
    }

    private fun setColorAndStroke() {
        val drawable = if (rounding == 8) {
            ContextCompat.getDrawable(context,
                R.drawable.box_button_background_8) as GradientDrawable
        } else {
            ContextCompat.getDrawable(context,
                R.drawable.box_button_background_4) as GradientDrawable
        }

        binding.text.setTextColor(ContextCompat.getColor(context, itemColor))
        binding.leftIcon.setColorFilter(ContextCompat.getColor(context, itemColor))
        binding.rightIcon.setColorFilter(ContextCompat.getColor(context, itemColor))
        drawable.setColor(
            if (type == LINE) Color.TRANSPARENT else ContextCompat.getColor(
                context,
                bgColor
            )
        )
        drawable.setStroke(
            context.dpToIntPx(if (type == LINE) 1F else 0F),
            ContextCompat.getColor(context, itemColor)
        )
        binding.boxButtonFrame.background = drawable
    }

    private fun setSize() {
        val layoutParams: LayoutParams = binding.boxButtonFrame.layoutParams as LayoutParams

        when (size) {
            ExtraLarge -> {
                layoutParams.height = context.dpToIntPx(56F)
                binding.text.typo = Typo.Button1
                binding.leftIcon.size = IconView.Medium
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(16F),
                    0,
                    context.dpToIntPx(16F),
                    0
                )
            }
            Large -> {
                layoutParams.height = context.dpToIntPx(48F)
                binding.text.typo = Typo.Button2
                binding.leftIcon.size = IconView.Medium
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(16F),
                    0,
                    context.dpToIntPx(16F),
                    0
                )
            }
            Medium -> {
                layoutParams.height = context.dpToIntPx(40F)
                binding.text.typo = Typo.Button2
                binding.leftIcon.size = IconView.Medium
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(12F),
                    0,
                    context.dpToIntPx(12F),
                    0
                )
            }
            Small -> {
                layoutParams.height = context.dpToIntPx(32F)
                binding.text.typo = Typo.Button4
                binding.leftIcon.size = IconView.ExtraSmall
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(12F),
                    0,
                    context.dpToIntPx(12F),
                    0
                )
            }
        }
        binding.boxButtonFrame.layoutParams = layoutParams
    }

    private fun setIcon() {
        when {
            (leftIcon != null) -> {
                leftIcon?.let {
                    binding.leftIcon.setIconResource(it)
                    binding.leftIcon.visibility = View.VISIBLE
                    binding.rightIcon.visibility = View.GONE
                }
            }
            (rightIcon != null) -> {
                rightIcon?.let {
                    binding.rightIcon.setIconResource(it)
                    binding.rightIcon.visibility = View.VISIBLE
                    binding.leftIcon.visibility = View.GONE
                }
            }
            else -> {
                binding.leftIcon.visibility = View.GONE
                binding.rightIcon.visibility = View.GONE
            }
        }
    }

    private fun setText() {
        binding.text.text = this.text
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isPressed = true
                    setBoxButtonInfo()
                }
                MotionEvent.ACTION_UP -> {
                    isPressed = false
                    setBoxButtonInfo()
                    performClick() // 손을 떼는 순간만 인식해야함
                }
            }
        }
        return true
    }


    companion object {
        const val FILLED = 0
        const val TINTED = 1
        const val LINE = 2

        const val ExtraLarge = 0
        const val Large = 1
        const val Medium = 2
        const val Small = 3

        @JvmStatic
        @BindingAdapter("type")
        fun setType(boxButton: BoxButton, type: Int) {
            boxButton.type = type
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(boxButton: BoxButton, size: Int) {
            boxButton.size = size
        }

        @JvmStatic
        @BindingAdapter("rounding")
        fun setRounding(boxButton: BoxButton, rounding: Int) {
            boxButton.rounding = rounding
        }

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setIsDisabled(boxButton: BoxButton, isDisabled: Boolean) {
            boxButton.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("isWarned")
        fun setIsWarned(boxButton: BoxButton, isWarned: Boolean) {
            boxButton.isWarned = isWarned
        }

        @JvmStatic
        @BindingAdapter("text")
        fun setText(boxButton: BoxButton, text: String) {
            boxButton.text = text
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText2(boxButton: BoxButton, text: String) {
            boxButton.text = text
        }
    }
}