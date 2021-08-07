package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.yourssu.design.R
import com.yourssu.design.system.language.ComponentGroup
import com.yourssu.design.undercarriage.size.dpToIntPx

class Divider : View {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    var direction: Int = HORIZONTAL
        set(direction) {
            field = direction
            setDividerInfo()
            requestLayout()
        }

    var dividerThickness: Int = THIN
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


    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Divider)
            direction =
                attributes.getInteger(R.styleable.Divider_direction, HORIZONTAL)

            dividerThickness = attributes.getInteger(R.styleable.Divider_dividerThickness, THIN)

            setDividerInfo()

            attributes.recycle()
        } else {
            setDividerInfo()
        }

    }

    private fun setDividerInfo() {
        val thickness = getThickness(dividerThickness)
        val color = getDividerColor(dividerThickness)

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

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1

        const val THIN = 0
        const val THICK = 1

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

        fun Context.divider(block: Divider.() -> Unit) = Divider(this).run {
            block.invoke(this)
            this
        }

        fun ViewGroup.divider(block: Divider.() -> Unit) = Divider(this.context).run {
            block.invoke(this)
            this@divider.addView(this)
            this
        }

        fun ComponentGroup.divider(block: Divider.() -> Unit) = Divider(this.componentContext).run {
            block.invoke(this)
            this@divider.addComponent(this)
            this
        }
    }
}