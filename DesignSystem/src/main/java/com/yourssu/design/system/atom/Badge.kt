package com.yourssu.design.system.atom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutBadgeBinding
import com.yourssu.design.system.foundation.Icon
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.system.foundation.ItemColor

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

    var text: String = ""
        set(value) {
            field = value
            setBadgeInfo()
            requestLayout()
        }

    var color: ItemColor = ItemColor.Mono
        set(color) {
            field = color
            setBadgeInfo()
            requestLayout()
        }

    @Icon.Iconography
    var icon: Int? = null
        set(icon) {
            field = icon
            setBadgeInfo()
            requestLayout()
        }

    private fun setBadgeInfo() {
        val color = getBadgeColor(color)

        val drawable =
            ContextCompat.getDrawable(context, R.drawable.badge_background) as GradientDrawable
        drawable.setColor(ContextCompat.getColor(context, color))
        binding.badgeFrame.background = drawable

        binding.badgeContent.text = text

        icon?.let {
            binding.badgeFrame.setPadding(context.dpToIntPx(8F), 0, context.dpToIntPx(8F), 0)
            binding.badgeIcon.visibility = View.VISIBLE
            binding.badgeIcon.setIconResource(it)
        } ?: run {
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
            badge.icon = icon
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