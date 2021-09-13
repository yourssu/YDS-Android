package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.PathParser
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.system.rule.normal
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.dpToPx


class ProfileImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(context, attrs, defStyleAttr) {

    @ProfileSize
    var size: Int = Small
        set(@ProfileSize value) {
            field = value
            requestLayout()
        }

    var highLight: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = widthMeasureSpec
        var height = heightMeasureSpec
        when (size) {
            Small -> {
                width = context.dpToIntPx(SmallSize)
                height = context.dpToIntPx(SmallSize)
            }
            Medium -> {
                width = context.dpToIntPx(MediumSize)
                height = context.dpToIntPx(MediumSize)
            }
            Large -> {
                width = context.dpToIntPx(LargeSize)
                height = context.dpToIntPx(LargeSize)
            }
            ExtraLarge -> {
                width = context.dpToIntPx(ExtraLargeSize)
                height = context.dpToIntPx(ExtraLargeSize)
            }
            ExtraSmall -> {
                width = context.dpToIntPx(ExtraSmallSize)
                height = context.dpToIntPx(ExtraSmallSize)
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
            color = context.resources.getColor(
                if (highLight)
                    R.color.borderNormal // TODO 하이라이트 기능이 들어가면 색상 변경
                else
                    R.color.borderNormal,
                context.theme)
            style = Paint.Style.STROKE
            strokeWidth = context.dpToPx(normal * 2) // 지정된 stroke width 는 1dp 지만 바깥쪽만 표시되기 때문에 2배
            isAntiAlias = true
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [Small, Medium, Large, ExtraLarge, ExtraSmall])
    annotation class ProfileSize

    companion object {
        const val Small = 0
        const val Medium = 1
        const val Large = 2
        const val ExtraLarge = 3
        const val ExtraSmall = 4

        private const val SmallSize = 36f
        private const val MediumSize = 48f
        private const val LargeSize = 72f
        private const val ExtraLargeSize = 96f
        private const val ExtraSmallSize = 32f

        private const val ExtraMargin =
            1f // stoke 가 path 를 기준으로 양쪽에 표시되기 때문에 짤린것처럼 보인다 이를 보안하기 위한 여분의 마진

        @JvmStatic
        @BindingAdapter("size")
        fun setProfileSize(profileImageView: ProfileImageView, @ProfileSize size: Int) {
            profileImageView.size = size
        }

        @JvmStatic
        @BindingAdapter("highLight")
        fun setHighLight(profileImageView: ProfileImageView, boolean: Boolean) {
            profileImageView.highLight = boolean
        }
    }
}