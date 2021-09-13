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
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutBadgeBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design_model.ItemColor

@SuppressLint("ViewConstructor")
class Badge : LinearLayout {
    constructor(context: Context) : super(context) {
        setBadgeInfo()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setBadgeInfo()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setBadgeInfo()
    }

    private val binding: LayoutBadgeBinding by lazy {
        LayoutBadgeBinding.inflate(
            LayoutInflater.from(
                context
            ), this, true
        )
    }

    private var hasIcon: Boolean = false
        set(value) {
            field = value
            setBadgeInfo()
            requestLayout()
        }

    private var text: String = ""
        set(value) {
            field = value
            setBadgeInfo()
            requestLayout()
        }

    private var color: ItemColor = ItemColor.Mono
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

    fun setBadgeIcon(@Icon.Iconography value: Int) {
        hasIcon = true
        icon = value
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

    companion object {

        @JvmStatic
        @BindingAdapter("text")
        fun setText(badge: Badge, text: String) {
            badge.text = text
        }

        @JvmStatic
        @BindingAdapter("icon")
        fun setIcon(badge: Badge, @Icon.Iconography icon: Int?) {
            if (icon == null) {
                badge.hasIcon = false
            } else {
                badge.hasIcon = true
                badge.icon = icon
            }
        }

        @JvmStatic
        @BindingAdapter("color")
        fun setColor(badge: Badge, color: ItemColor) {
            badge.color = color
        }
    }

    @ColorRes
    private fun getBadgeColor(color: ItemColor) = when (color) {
        ItemColor.Mono -> R.color.monoItemBG
        ItemColor.Green -> R.color.greenItemBG
        ItemColor.Emerald -> R.color.emeraldItemBG
        ItemColor.Aqua -> R.color.aquaItemBG
        ItemColor.Blue -> R.color.blueItemBG
        ItemColor.Indigo -> R.color.indigoItemBG
        ItemColor.Violet -> R.color.violetItemBG
        ItemColor.Purple -> R.color.purpleItemBG
        ItemColor.Pink -> R.color.pinkItemBG
    }
}