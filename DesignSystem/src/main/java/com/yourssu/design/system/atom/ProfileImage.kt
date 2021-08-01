package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.PathParser
import com.yourssu.design.R
import com.yourssu.design.undercarriage.size.dpToIntPx


class ProfileImage: AppCompatImageView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    @ProfileSize
    var size: Int = Small
        set(@ProfileSize value) {
            field = value
            requestLayout()
        }

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProfileImage)

            size = typedArray.getInteger(R.styleable.ProfileImage_size, Small)

            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = widthMeasureSpec
        var height = heightMeasureSpec
        when (size) {
            Small -> { width = context.dpToIntPx(SmallSize); height = context.dpToIntPx(SmallSize) }
            Medium -> { width = context.dpToIntPx(MediumSize); height = context.dpToIntPx(MediumSize) }
            Large -> { width = context.dpToIntPx(LargeSize); height = context.dpToIntPx(LargeSize) }
            ExtraLarge -> { width = context.dpToIntPx(ExtraLargeSize); height = context.dpToIntPx(ExtraLargeSize) }
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(getSquirclePath())
        super.onDraw(canvas)
    }

    private fun getSquirclePath(): Path {
        val squirclePath = PathParser.createPathFromPathData(
            context.resources.getString(R.string.squircle_path_data)
        )
        val scaleMatrix = Matrix()
        val pathRectF = RectF()
        squirclePath.computeBounds(pathRectF, true)
        scaleMatrix.postScale(width / pathRectF.width(), height / pathRectF.height())
        squirclePath.transform(scaleMatrix)
        return squirclePath
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [Small, Medium, Large, ExtraLarge])
    annotation class ProfileSize

    companion object {
        const val Small = 0
        const val Medium = 1
        const val Large = 2
        const val ExtraLarge = 3

        private const val SmallSize = 36f
        private const val MediumSize = 48f
        private const val LargeSize = 72f
        private const val ExtraLargeSize = 96f
    }
}