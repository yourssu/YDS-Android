package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutBadgeBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.size.dpToIntPx

@SuppressLint("ViewConstructor")
class Badge : LinearLayout {
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

    private val binding: LayoutBadgeBinding by lazy {
        LayoutBadgeBinding.inflate(
            LayoutInflater.from(
                context
            ), this, true
        )
    }

    private var hasIcon: Boolean = true

    private var text: String = ""

    @BadgeColor
    private var color: Int = MONO
        set(color) {
            field = color
            setBadgeInfo()
            requestLayout()
        }

    @Icon.Iconography
    private var icon: Int = Icon.ic_adbadge_filled
        set(icon) {
            field = icon
            setBadgeInfo()
            requestLayout()
        }

    fun setText(value: String) {
        text = value
    }

    fun setBadgeColor(@BadgeColor value: Int) {
        color = value
    }

    fun setBadgeIcon(@Icon.Iconography value: Int) {
        hasIcon = true
        icon = value
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Badge)

            hasIcon = typedArray.hasValue(R.styleable.Badge_badgeIcon)

            text = typedArray.getString(R.styleable.Badge_android_text).toString()
            color = typedArray.getInteger(R.styleable.Badge_badgeColor, MONO)
            icon = typedArray.getInteger(R.styleable.Badge_badgeIcon, Icon.ic_adbadge_filled)

            setBadgeInfo()

            typedArray.recycle()
        } else {
            setBadgeInfo()
        }
    }

    private fun setBadgeInfo() {
        val color = getBadgeColor(color)

        val drawable =
            ContextCompat.getDrawable(context, R.drawable.badge_background) as GradientDrawable
        drawable.setColor(ContextCompat.getColor(context, color))
        binding.badgeFrame.background = drawable

        binding.badgeContent.text = text

        if (hasIcon) {
            binding.badgeFrame.setPadding(context.dpToIntPx(8F), 0, context.dpToIntPx(8F), 0)
            binding.badgeIcon.visibility = View.VISIBLE
            binding.badgeIcon.setIconResource(icon)
        } else {
            binding.badgeFrame.setPadding(context.dpToIntPx(12F), 0, context.dpToIntPx(12F), 0)
            binding.badgeIcon.visibility = View.GONE
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [MONO, GREEN, EMERALD, AQUA, BLUE, INDIGO, VIOLET, PURPLE, PINK])
    annotation class BadgeColor

    companion object {
        const val MONO: Int = 0
        const val GREEN: Int = 1
        const val EMERALD: Int = 2
        const val AQUA: Int = 3
        const val BLUE: Int = 4
        const val INDIGO: Int = 5
        const val VIOLET: Int = 6
        const val PURPLE: Int = 7
        const val PINK: Int = 8
    }

    @ColorRes
    private fun getBadgeColor(color: Int) = when (color) {
        MONO -> R.color.monoItemBG
        GREEN -> R.color.greenItemBG
        EMERALD -> R.color.emeraldItemBG
        AQUA -> R.color.aquaItemBG
        BLUE -> R.color.blueItemBG
        INDIGO -> R.color.indigoItemBG
        VIOLET -> R.color.violetItemBG
        PURPLE -> R.color.purpleItemBG
        PINK -> R.color.pinkItemBG
        else -> R.color.monoItemBG
    }
}