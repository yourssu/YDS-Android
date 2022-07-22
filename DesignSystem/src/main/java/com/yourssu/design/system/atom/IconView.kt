package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.size.dpToIntPx

class IconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        setIconResource()
    }

    @IconSize
    var size: Int = Medium
        set(@IconSize value) {
            field = value
            requestLayout()
        }

    @Icon.Iconography
    var icon: Int = Icon.ic_adbadge_filled
        set(@Icon.Iconography value) {
            field = value
            setIconResource()
        }

    fun setIconViewSize(@IconSize value: Int) {
        size = value
    }

    fun setIconResource(@Icon.Iconography value: Int) {
        icon = value
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = widthMeasureSpec
        var height = heightMeasureSpec
        when (size) {
            ExtraSmall -> {
                width = context.dpToIntPx(ExtraSmallSize); height =
                    context.dpToIntPx(ExtraSmallSize)
            }
            Small -> {
                width = context.dpToIntPx(SmallSize); height = context.dpToIntPx(SmallSize)
            }
            Medium -> {
                width = context.dpToIntPx(MediumSize); height = context.dpToIntPx(MediumSize)
            }
            Large -> {
                width = context.dpToIntPx(LargeSize); height = context.dpToIntPx(LargeSize)
            }
        }
        setMeasuredDimension(width, height)
    }

    private fun setIconResource() {
        setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                Icon.getIconDrawable(icon)
            )
        )
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [ExtraSmall, Small, Medium, Large])
    annotation class IconSize

    companion object {
        const val ExtraSmall = 0
        const val Small = 1
        const val Medium = 2
        const val Large = 3

        private const val ExtraSmallSize = 16f
        private const val SmallSize = 20f
        private const val MediumSize = 24f
        private const val LargeSize = 48f

        @JvmStatic
        @BindingAdapter("icon")
        fun setIcon(iconView: IconView, @Icon.Iconography icon: Int) {
            iconView.icon = icon
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(iconView: IconView, @IconSize size: Int) {
            iconView.size = size
        }
    }
}