package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutTopBarButtonBinding
import com.yourssu.design.system.foundation.Icon

@SuppressLint("ViewConstructor")
class TopBarButton : LinearLayout {
    constructor(context: Context) : super(context) {
        setTopBarButtonInfo()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setTopBarButtonInfo()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setTopBarButtonInfo()
    }

    var isDisabled = false
        set(value) {
            field = value
            setTopBarButtonInfo()
            requestLayout()
            isEnabled = !isDisabled
        }

    var text = ""
        set(value) {
            field = value
            binding.topBarButtonText.text = text
            setTopBarButtonInfo()
            requestLayout()
        }

    @Icon.Iconography
    var icon: Int? = null
        set(@Icon.Iconography value) {
            field = value
            if (value != null) {
                hasIcon = true
                binding.topBarButtonIcon.icon = value
            } else {
                hasIcon = false
            }
            setTopBarButtonInfo()
            requestLayout()
        }

    private var hasIcon: Boolean = false


    private val binding: LayoutTopBarButtonBinding by lazy {
        LayoutTopBarButtonBinding.inflate(LayoutInflater.from(context), this, true)
    }


    private fun setTopBarButtonInfo() {
        setButtonVisibility()
        setButtonColor()
    }

    private fun setButtonVisibility() {
        binding.topBarButtonText.isVisible = !hasIcon && text.isNotEmpty()
        binding.topBarButtonIcon.isVisible = hasIcon
    }

    private fun setButtonColor() {
        if (isDisabled) {
            setAtomTint(context.getColor(R.color.buttonDisabled))
        } else {
            setAtomTint(context.getColor(R.color.buttonNormal))
        }
    }

    private fun setButtonColorPressed() {
        setAtomTint(context.getColor(R.color.buttonNormalPressed))
    }

    private fun setAtomTint(@ColorInt color: Int) {
        binding.topBarButtonIcon.setColorFilter(color)
        binding.topBarButtonText.setTextColor(color)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isDisabled) {
                    setButtonColorPressed()
                }
            }

            MotionEvent.ACTION_UP -> {
                setTopBarButtonInfo()
            }
        }
        return true
    }


    companion object {
        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(topBarButton: TopBarButton, isDisabled: Boolean) {
            topBarButton.isDisabled = isDisabled
        }


        @JvmStatic
        @BindingAdapter("text")
        fun setText(topBarButton: TopBarButton, text: String) {
            topBarButton.text = text
        }

        @JvmStatic
        @BindingAdapter("icon")
        fun setIcon(topBarButton: TopBarButton, @Icon.Iconography icon: Int) {
            topBarButton.icon = icon
        }
    }
}