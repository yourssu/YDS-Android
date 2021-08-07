package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.yourssu.design.R
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.size.dpToIntPx

class IconView: AppCompatImageView {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
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

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconView)

            size = typedArray.getInteger(R.styleable.IconView_iconViewSize, Medium)
            icon = typedArray.getInteger(R.styleable.IconView_iconResource, Icon.ic_adbadge_filled)

            setIconResource()

            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = widthMeasureSpec
        var height = heightMeasureSpec
        when (size) {
            ExtraSmall -> { width = context.dpToIntPx(ExtraSmallSize); height = context.dpToIntPx(ExtraSmallSize) }
            Small -> { width = context.dpToIntPx(SmallSize); height = context.dpToIntPx(SmallSize) }
            Medium -> { width = context.dpToIntPx(MediumSize); height = context.dpToIntPx(MediumSize) }
            Large -> { width = context.dpToIntPx(LargeSize); height = context.dpToIntPx(LargeSize) }
        }
        setMeasuredDimension(width, height)
    }

    private fun setIconResource() {
        setImageDrawable(ResourcesCompat.getDrawable(resources, Icon.getIconDrawable(icon), context.theme))
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
    }
}