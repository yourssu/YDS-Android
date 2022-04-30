package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutListItemBinding
import com.yourssu.design.system.foundation.Icon

class ListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    @Icon.Iconography
    var leftIcon: Int? = null
        set(@Icon.Iconography value) {
            field = value
            setListItemInfo()
        }

    @Icon.Iconography
    var rightIcon: Int? = null
        set(@Icon.Iconography value) {
            field = value
            setListItemInfo()
        }

    var text: String = ""
        set(value) {
            field = value
            setListItemInfo()
        }

    private val binding: LayoutListItemBinding by lazy {
        LayoutListItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun setListItemInfo() {
        binding.listItemText.text = text

        binding.iconLeft.isVisible = (leftIcon != null)
        leftIcon?.apply { binding.iconLeft.icon = this }

        binding.iconRight.isVisible = (rightIcon != null)
        rightIcon?.apply { binding.iconRight.icon = this }

        if (isPressed) {
            setBackgroundColor(ContextCompat.getColor(context, R.color.bgPressed))
        } else {
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        when (event?.action) {
            ACTION_DOWN -> {
                isPressed = true
                setListItemInfo()
            }

            ACTION_UP, ACTION_CANCEL -> {
                isPressed = false
                setListItemInfo()
            }

        }

        return true
    }

    companion object {

        @JvmStatic
        @BindingAdapter("leftIcon")
        fun setLeftIcon(listItem: ListItem, @Icon.Iconography leftIcon: Int?) {
            listItem.leftIcon = leftIcon
        }

        @JvmStatic
        @BindingAdapter("rightIcon")
        fun setRightIcon(listItem: ListItem, @Icon.Iconography rightIcon: Int?) {
            listItem.rightIcon = rightIcon
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(listItem: ListItem, text: String?) {
            listItem.text = text ?: ""
        }
    }

}