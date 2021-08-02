package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.PathParser
import com.yourssu.design.R
import com.yourssu.design.system.rule.normal
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.dpToPx


class ProfileImageView : AppCompatImageView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
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
            Small -> {
                width = context.dpToIntPx(SmallSize); height = context.dpToIntPx(SmallSize)
            }
            Medium -> {
                width = context.dpToIntPx(MediumSize); height = context.dpToIntPx(MediumSize)
            }
            Large -> {
                width = context.dpToIntPx(LargeSize); height = context.dpToIntPx(LargeSize)
            }
            ExtraLarge -> {
                width = context.dpToIntPx(ExtraLargeSize); height =
                    context.dpToIntPx(ExtraLargeSize)
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(getSquircleStrokePath(), getStrokePaint())
        canvas?.clipPath(getSquircleImagePath())
        super.onDraw(canvas)
    }

    private fun getSquircleImagePath(): Path {
        val squirclePath = PathParser.createPathFromPathData(
            context.resources.getString(R.string.squircle_path_data)
        )
        val scaleMatrix = Matrix()
        val pathRectF = RectF()
        squirclePath.computeBounds(pathRectF, true)
        scaleMatrix.postScale(
            (width - context.dpToPx(ExtraMargin + (normal * 2))) / pathRectF.width(),
            (height - context.dpToPx(ExtraMargin + (normal * 2))) / pathRectF.height()
        )
        scaleMatrix.postTranslate(context.dpToPx(normal), context.dpToPx(normal))
        squirclePath.transform(scaleMatrix)
        return squirclePath
    }

    private fun getSquircleStrokePath(): Path {
        val squirclePath = PathParser.createPathFromPathData(
            context.resources.getString(R.string.squircle_path_data)
        )
        val scaleMatrix = Matrix()
        val pathRectF = RectF()
        squirclePath.computeBounds(pathRectF, true)
        scaleMatrix.postScale(
            (width - context.dpToPx(ExtraMargin + normal)) / pathRectF.width(),
            (height - context.dpToPx(ExtraMargin + normal)) / pathRectF.height()
        )
        scaleMatrix.postTranslate(context.dpToPx(normal / 2), context.dpToPx(normal / 2))
        squirclePath.transform(scaleMatrix)
        return squirclePath
    }

    private fun getStrokePaint(): Paint {
        val strokePaint = Paint()
        return strokePaint.apply {
            color = context.resources.getColor(R.color.borderNormal, context.theme)
            style = Paint.Style.STROKE
            strokeWidth = context.dpToPx(normal * 2) // 지정된 stroke width 는 1dp 지만 바깥쪽만 표시되기 때문에 2배
            isAntiAlias = true
        }
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

        private const val ExtraMargin = 1f // stoke 가 path 를 기준으로 양쪽에 표시되기 때문에 짤린것처럼 보인다 이를 보안하기 위한 여분의 마진
    }
}