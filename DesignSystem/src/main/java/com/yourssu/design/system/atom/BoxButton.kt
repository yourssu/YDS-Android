package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
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
            requestLayout()
        }

    var size: Int = 0
        set(size) {
            field = size
            setBoxButtonInfo()
            requestLayout()
        }

    var rounding: Int = 4
        set(rounding) {
            field = rounding
            setBoxButtonInfo()
            requestLayout()
        }

    var isDisabled: Boolean = false
        set(isDisabled) {
            field = isDisabled
            setBoxButtonInfo()
            requestLayout()
        }

    var isWarned: Boolean = false
        set(isWarned) {
            field = isWarned
            setBoxButtonInfo()
            requestLayout()
        }

    var text: String = ""
        set(text) {
            field = text
            setBoxButtonInfo()
            requestLayout()
        }

    @Icon.Iconography
    var leftIcon: Int = 0
        set(leftIcon) {
            field = leftIcon
            hasLeftIcon = true
            setBoxButtonInfo()
            requestLayout()
        }

    @Icon.Iconography
    var rightIcon: Int = 0
        set(rightIcon) {
            field = rightIcon
            hasRightIcon = true
            setBoxButtonInfo()
            requestLayout()
        }

    private var hasLeftIcon: Boolean = false
        set(hasLeftIcon) {
            field = hasLeftIcon
            setBoxButtonInfo()
            requestLayout()
        }

    private var hasRightIcon: Boolean = false
        set(hasRightIcon) {
            field = hasRightIcon
            setBoxButtonInfo()
            requestLayout()
        }

    @ColorRes
    private var itemColor: Int = 0

    @ColorRes
    private var bgColor: Int = 0

    private fun setBoxButtonInfo() {
        setTheme()
        setIcon()
        setText()
    }

/*
    private fun isValid(): Boolean {
        when (type) {
            FILLED -> if (size == Small) return false
            TINTED -> if (size == ExtraLarge) return false
            LINE -> if (size == ExtraLarge) return false
        }

        when (rounding) {
            8 -> if (size == Medium || size == Small) return false
            4 -> if (size == ExtraLarge) return false
            else -> if (size == ExtraLarge) return false
        }

        return true
    }
 */

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
    }

    private fun setFilledTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.buttonDisabled
                bgColor = R.color.buttonDisabledBG
            }
            isWarned -> {
                itemColor = R.color.buttonReversed
                bgColor = R.color.buttonWarned
            }
            else -> {
                itemColor = R.color.buttonReversed
                bgColor = R.color.buttonPoint
            }
        }
        setColorAndStroke()
    }

    private fun setTintedTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.buttonDisabled
                bgColor = R.color.buttonDisabledBG
            }
            isWarned -> {
                itemColor = R.color.buttonWarned
                bgColor = R.color.buttonWarnedBG
            }
            else -> {
                itemColor = R.color.buttonPoint
                bgColor = R.color.buttonPointBG
            }
        }
        setColorAndStroke()
    }

    private fun setLineTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.buttonDisabled
            }
            isWarned -> {
                itemColor = R.color.buttonWarned
            }
            else -> {
                itemColor = R.color.buttonPoint
            }
        }
        setColorAndStroke()
    }

    private fun setColorAndStroke() {
        val drawable =
            ContextCompat.getDrawable(context, R.drawable.box_button_background) as GradientDrawable

        binding.text.setTextColor(ContextCompat.getColor(context, itemColor))
        binding.leftIcon.setColorFilter(ContextCompat.getColor(context, itemColor))
        binding.rightIcon.setColorFilter(ContextCompat.getColor(context, itemColor))
        drawable.setColor(
            if (type == LINE) Color.TRANSPARENT else ContextCompat.getColor(
                context,
                bgColor
            )
        )
        drawable.cornerRadius =
            context.dpToPx(if (rounding == 8 || rounding == 4) rounding.toFloat() else 4F)
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
                binding.leftIcon.size = IconView.Small
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
            hasLeftIcon -> {
                binding.leftIcon.setIconResource(leftIcon)
                binding.leftIcon.visibility = View.VISIBLE
                binding.rightIcon.visibility = View.GONE
            }
            hasRightIcon -> {
                binding.rightIcon.setIconResource(rightIcon)
                binding.rightIcon.visibility = View.VISIBLE
                binding.leftIcon.visibility = View.GONE
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
                    when (bgColor) {
                        R.color.buttonPoint -> {
                            bgColor = R.color.buttonPointPressed
                            setColorAndStroke()
                        }
                        R.color.buttonWarned -> {
                            bgColor = R.color.buttonWarnedPressed
                            setColorAndStroke()
                        }
                    }
                    when (itemColor) {
                        R.color.buttonPoint -> {
                            itemColor = R.color.buttonPointPressed
                            setColorAndStroke()
                        }
                        R.color.buttonWarned -> {
                            itemColor = R.color.buttonWarnedPressed
                            setColorAndStroke()
                        }
                    }
                    performClick()
                }
                MotionEvent.ACTION_UP -> {
                    when (bgColor) {
                        R.color.buttonPointPressed -> {
                            bgColor = R.color.buttonPoint
                            setColorAndStroke()
                        }
                        R.color.buttonWarnedPressed -> {
                            bgColor = R.color.buttonWarned
                            setColorAndStroke()
                        }
                    }
                    when (itemColor) {
                        R.color.buttonPointPressed -> {
                            itemColor = R.color.buttonPoint
                            setColorAndStroke()
                        }
                        R.color.buttonWarnedPressed -> {
                            itemColor = R.color.buttonWarned
                            setColorAndStroke()
                        }
                    }
                    performClick()
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
    }
}