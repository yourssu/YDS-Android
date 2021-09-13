package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.CompoundButton
import com.yourssu.design.R
import com.yourssu.design.undercarriage.size.dpToIntPx

class Toggle : CompoundButton {
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
            setToggleInfo()
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
        setToggleInfo()
    }

    fun setToggleDisabled(disabled: Boolean) {
        isDisabled = disabled
    }

    fun setToggleSelected(selected: Boolean) {
        isSelected = selected
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (!isDisabled && isEnabled) {
                isSelected = !isSelected
                setToggleInfo()
            }
        }

        return super.onTouchEvent(event)
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
    }


}
