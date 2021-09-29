package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.*
import androidx.core.content.ContextCompat
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

    @ColorInt
    private var dividerColor: Int = ContextCompat.getColor(context, R.color.borderNormal)

    @Px
    private var dividerThicknessInPx: Int = 0

    private var dividerDrawable: ShapeDrawable = ShapeDrawable()

    private fun setDividerInfo() {
        val thickness = getThickness(thickness)
        val color = getDividerColor(this.thickness)

        dividerThicknessInPx =
            context.dpToIntPx(resources.getDimensionPixelSize(thickness).toFloat())
        dividerColor = context.getColor(color)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        if (direction == HORIZONTAL) {
            setMeasuredDimension(width, dividerThicknessInPx)
        } else {
            setMeasuredDimension(dividerThicknessInPx, height)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (direction == HORIZONTAL) {
            dividerDrawable.setBounds(0, 0, width, dividerThicknessInPx)
        } else {
            dividerDrawable.setBounds(0, 0, dividerThicknessInPx, height)
        }
        dividerDrawable.paint.color = dividerColor
        dividerDrawable.draw(canvas)
    }

    @DimenRes
    private fun getThickness(thickness: Int) = when (thickness) {
        THIN -> R.dimen.thin
        THICK -> R.dimen.thick
        else -> R.dimen.thin
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