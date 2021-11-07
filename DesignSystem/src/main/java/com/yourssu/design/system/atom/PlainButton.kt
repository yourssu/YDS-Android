package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutPlainButtonBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.system.foundation.Typo

class PlainButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    var isDisabled: Boolean = false
        set(value) {
            field = value
            setButtonInfo()
        }

    var isWarned: Boolean = false
        set(value) {
            field = value
            setButtonInfo()
        }

    var isPointed: Boolean = false
        set(value) {
            field = value
            setButtonInfo()
        }


    @PlainButtonSize
    var size: Int = MEDIUM
        set(@PlainButtonSize value) {
            field = value
            setButtonInfo()
        }

    var text: String = ""
        set(value) {
            field = value
            binding.plainButtonText.text = field
            setButtonInfo()
        }

    @Icon.Iconography
    var leftIcon: Int? = null
        set(@Icon.Iconography icon) {
            field = icon
            if (icon != null) {
                binding.plainButtonLeftIcon.icon = icon
            }
            setButtonInfo()
        }

    @Icon.Iconography
    var rightIcon: Int? = null
        set(@Icon.Iconography icon) {
            field = icon
            if (icon != null) {
                binding.plainButtonRightIcon.icon = icon
            }
            setButtonInfo()
        }

    private val binding: LayoutPlainButtonBinding by lazy {
        LayoutPlainButtonBinding.inflate(LayoutInflater.from(context), this, true)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                isPressed = true
                setButtonInfo()
            }

            MotionEvent.ACTION_UP -> {
                isPressed = false
                setButtonInfo()
            }
        }

        return true
    }

    private fun setButtonInfo() {
        if (isPressed)
            setButtonColorPressed()
        else
            setButtonColor()

        when (size) {
            LARGE -> {
                setButtonAtomVisibility(false)
                binding.plainButtonCenterIcon.isVisible = true
                binding.plainButtonCenterIcon.size = IconView.Medium
                setCenterIconResource()
            }
            MEDIUM -> {
                if (binding.plainButtonCenterIcon.isVisible) {
                    binding.plainButtonCenterIcon.isVisible = false
                    setButtonAtomVisibility(true)
                } else {
                    binding.plainButtonText.typo = Typo.Button3
                    binding.plainButtonLeftIcon.size = IconView.Small
                    binding.plainButtonRightIcon.size = IconView.Small
                }

                if (text == "") {
                    setButtonAtomVisibility(false)
                    binding.plainButtonCenterIcon.isVisible = true
                    binding.plainButtonCenterIcon.size = IconView.Small
                    setCenterIconResource()
                }
            }
            SMALL -> {
                if (binding.plainButtonCenterIcon.isVisible) {
                    binding.plainButtonCenterIcon.isVisible = false
                    setButtonAtomVisibility(true)
                } else {
                    binding.plainButtonText.typo = Typo.Button4
                    binding.plainButtonLeftIcon.size = IconView.ExtraSmall
                    binding.plainButtonRightIcon.size = IconView.ExtraSmall
                }

                if (text == "") {
                    setButtonAtomVisibility(false)
                    binding.plainButtonCenterIcon.isVisible = true
                    binding.plainButtonCenterIcon.size = IconView.ExtraSmall
                    setCenterIconResource()
                }

            }
        }

        setHorizontalIconVisibility()
    }

    private fun setButtonColor() {
        when {
            isDisabled -> setAtomTint(context.getColor(R.color.buttonDisabled))
            isWarned -> setAtomTint(context.getColor(R.color.buttonWarned))
            isPointed -> setAtomTint(context.getColor(R.color.buttonPoint))
            else -> setAtomTint(context.getColor(R.color.buttonNormal))
        }
    }

    private fun setButtonColorPressed() {
        when {
            isDisabled -> setAtomTint(context.getColor(R.color.buttonDisabled))
            isWarned -> setAtomTint(context.getColor(R.color.buttonWarnedPressed))
            isPointed -> setAtomTint(context.getColor(R.color.buttonPointPressed))
            else -> setAtomTint(context.getColor(R.color.buttonNormalPressed))
        }
    }

    private fun setHorizontalIconVisibility() {
        if (binding.plainButtonCenterIcon.isVisible)
            return

        if (leftIcon == null && rightIcon == null) {
            binding.plainButtonRightIcon.isVisible = false
            binding.plainButtonLeftIcon.isVisible = false
        } else if (leftIcon == null && rightIcon != null) {
            binding.plainButtonLeftIcon.isVisible = false
            binding.plainButtonRightIcon.isVisible = true
        } else {
            binding.plainButtonLeftIcon.isVisible = true
            binding.plainButtonRightIcon.isVisible = false
        }
    }

    private fun setCenterIconResource() {
        when {
            leftIcon != null -> {
                binding.plainButtonCenterIcon.icon = leftIcon!!
            }
            rightIcon != null -> {
                binding.plainButtonCenterIcon.icon = rightIcon!!
            }
            else -> {
                binding.plainButtonCenterIcon.icon = Icon.ic_adbadge_filled
            }
        }
    }


    private fun setAtomTint(@ColorInt color: Int) {
        binding.plainButtonLeftIcon.setColorFilter(color)
        binding.plainButtonRightIcon.setColorFilter(color)
        binding.plainButtonCenterIcon.setColorFilter(color)
        binding.plainButtonText.setTextColor(color)
    }

    private fun setButtonAtomVisibility(value: Boolean) {
        binding.plainButtonLeftIcon.isVisible = value
        binding.plainButtonRightIcon.isVisible = value
        binding.plainButtonText.isVisible = value
    }


    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [SMALL, MEDIUM, LARGE])
    annotation class PlainButtonSize

    companion object {
        const val SMALL = 0
        const val MEDIUM = 1
        const val LARGE = 2

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(plainButton: PlainButton, isDisabled: Boolean) {
            plainButton.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("isWarned")
        fun setWarned(plainButton: PlainButton, isWarned: Boolean) {
            plainButton.isWarned = isWarned
        }

        @JvmStatic
        @BindingAdapter("isPointed")
        fun setPointed(plainButton: PlainButton, isPointed: Boolean) {
            plainButton.isPointed = isPointed
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(plainButton: PlainButton, @PlainButtonSize size: Int) {
            plainButton.size = size
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(plainButton: PlainButton, text: String) {
            plainButton.text = text
        }

        @JvmStatic
        @BindingAdapter("leftIcon")
        fun setLeftIcon(plainButton: PlainButton, @Icon.Iconography icon: Int) {
            plainButton.leftIcon = icon
        }

        @JvmStatic
        @BindingAdapter("rightIcon")
        fun setRightIcon(plainButton: PlainButton, @Icon.Iconography icon: Int) {
            plainButton.rightIcon = icon
        }


    }

}