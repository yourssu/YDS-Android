package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.*
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.undercarriage.size.dpToIntPx

class Divider : View {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setDividerInfo()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    @Direction
    private var direction: Int = HORIZONTAL
        set(direction) {
            field = direction
            setDividerInfo()
            requestLayout()
        }

    @Thickness
    private var thickness: Int = THIN
        set(thickness) {
            field = thickness
            setDividerInfo()
            requestLayout()
        }

    @Px
    private var dividerThicknessInPx: Int = 0

    private fun setDividerInfo() {
        val thickness = getThickness(thickness)
        val color = getDividerColor(this.thickness)

        setBackgroundColor(context.getColor(color))

        dividerThicknessInPx = context.dpToIntPx(thickness)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if (direction == HORIZONTAL) {
            setMeasuredDimension(widthMeasureSpec, dividerThicknessInPx)
        } else {
            setMeasuredDimension(dividerThicknessInPx, heightMeasureSpec)
        }
    }

    private fun getThickness(thickness: Int) = when (thickness) {
        THIN -> 0.34f
        THICK -> 8f
        else -> 0.34f
    }

    @ColorRes
    private fun getDividerColor(thickness: Int) = when (thickness) {
        THIN -> R.color.borderNormal
        THICK -> R.color.borderThin
        else -> R.color.borderNormal
    }

    @IntDef(value = [HORIZONTAL, VERTICAL])
    annotation class Direction

    @IntDef(value = [THIN, THICK])
    annotation class Thickness

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1

        const val THIN = 0
        const val THICK = 1

        @JvmStatic
        @BindingAdapter("direction")
        fun setDirection(divider: Divider, @Direction direction: Int) {
            divider.direction = direction
        }

        @JvmStatic
        @BindingAdapter("thickness")
        fun setThickness(divider: Divider, @Thickness thickness: Int) {
            divider.thickness = thickness
        }
    }
}