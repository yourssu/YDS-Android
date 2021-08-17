package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SwitchCompat
import com.yourssu.design.R

class Toggle : SwitchCompat {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    var isDisabled: Boolean = false
        set(isDisabled) {
            field = isDisabled
            isEnabled = !isDisabled
            setToggleInfo()
            invalidate()
        }

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Toggle)
            isDisabled = attributes.getBoolean(R.styleable.Toggle_toggleDisabled, false)
            isSelected = attributes.getBoolean(R.styleable.Toggle_toggleSelected, false)

            setToggleInfo()
            attributes.recycle()
        } else {
            setToggleInfo()
        }
    }

    override fun isSelected(): Boolean {
        return isChecked
    }

    override fun setSelected(selected: Boolean) {
        isChecked = selected
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        setToggleInfo()
    }

    fun setToggleDisabled(disabled: Boolean) {
        isDisabled = disabled
    }

    fun setToggleSelected(selected: Boolean) {
        isSelected = selected
    }

    private fun setToggleInfo() {
        trackDrawable = if (isSelected && !isDisabled) {
            AppCompatResources.getDrawable(context, R.drawable.toggle_track_selected)
        } else {
            AppCompatResources.getDrawable(context, R.drawable.toggle_track_unselected)
        }

        thumbDrawable = if (isDisabled) {
            AppCompatResources.getDrawable(context, R.drawable.toggle_thumb_disabled)
        } else {
            AppCompatResources.getDrawable(context, R.drawable.toggle_thumb_enabled)
        }

        background = null // Thumb 잔물결 효과 비활성화
    }
}