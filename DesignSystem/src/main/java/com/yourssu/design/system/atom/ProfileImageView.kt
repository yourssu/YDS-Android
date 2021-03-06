package com.yourssu.design.system.atom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
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
                    R.color.borderNormal // TODO ??????????????? ????????? ???????????? ?????? ??????
                else
                    R.color.borderNormal,
                context.theme)
            style = Paint.Style.STROKE
            strokeWidth = context.dpToPx(normal * 2) // ????????? stroke width ??? 1dp ?????? ???????????? ???????????? ????????? 2???
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

        private const val SmallSize = 32f
        private const val MediumSize = 48f
        private const val LargeSize = 72f
        private const val ExtraLargeSize = 96f
        private const val ExtraSmallSize = 24f

        private const val ExtraMargin =
            1f // stoke ??? path ??? ???????????? ????????? ???????????? ????????? ??????????????? ????????? ?????? ???????????? ?????? ????????? ??????

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

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUri(view: ProfileImageView, imageUri: String?) {
            if (imageUri == null) {
                view.setImageURI(null)
            } else {
                view.setImageURI(Uri.parse(imageUri))
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUri(view: ProfileImageView, imageUri: Uri?) {
            view.setImageURI(imageUri)
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageDrawable(view: ProfileImageView, drawable: Drawable?) {
            view.setImageDrawable(drawable)
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageResource(imageView: ProfileImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }
}